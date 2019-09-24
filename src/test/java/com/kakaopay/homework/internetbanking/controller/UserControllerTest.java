package com.kakaopay.homework.internetbanking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kakaopay.homework.internetbanking.config.CacheConfiguration;
import com.kakaopay.homework.internetbanking.config.security.JwtTokenProvider;
import com.kakaopay.homework.internetbanking.domain.user.Role;
import com.kakaopay.homework.internetbanking.domain.user.RoleType;
import com.kakaopay.homework.internetbanking.domain.user.User;
import com.kakaopay.homework.internetbanking.payload.LoginRequest;
import com.kakaopay.homework.internetbanking.payload.SignupRequest;
import com.kakaopay.homework.internetbanking.payload.UserResponse;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import com.kakaopay.homework.internetbanking.service.user.CustomUserDetailsService;
import com.kakaopay.homework.internetbanking.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import({ JwtTokenProvider.class, CustomUserDetailsService.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    JwtTokenProvider tokenProvider;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private ObjectWriter ow;

    @Before
    public void setup(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    public void 회원가입_테스트() throws Exception {
        //given
        final SignupRequest signupRequest = SignupRequest.builder()
                                            .userId("holeman79")
                                            .password("12345")
                                            .build();

        given(userService.addUser(any())).willReturn(true);

        String requestJson = ow.writeValueAsString(signupRequest);

        //when
        final ResultActions actions = mvc.perform(post("/api/user/signup")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

        //then
        actions.andExpect(status().isOk());
    }

    @Test
    public void 로그인_테스트() throws Exception{
        //given
        final LoginRequest loginRequest = LoginRequest.builder()
                .userId("holeman79")
                .password("12345")
                .build();

        final Role role = new Role();
        role.setId(1L);
        role.setName(RoleType.USER);

        String accessToken = tokenProvider.generateToken(1L);
        final UserResponse userResponse = UserResponse.builder()
                .id(85L)
                .userId("holeman79")
                .accessToken(accessToken)
                .role(role)
                .build();

        given(userService.login(any())).willReturn(userResponse);

        String requestJson = ow.writeValueAsString(loginRequest);

        //when
        final ResultActions actions = mvc.perform(post("/api/user/login")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(85L))
                .andExpect(jsonPath("$.userId").value("holeman79"))
                .andExpect(jsonPath("$.accessToken").value(accessToken))
                .andExpect(jsonPath("$.role.name").value("USER"));
    }
}
