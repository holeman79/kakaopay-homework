package com.kakaopay.homework.internetbanking.service;

import com.kakaopay.homework.internetbanking.config.security.JwtTokenProvider;
import com.kakaopay.homework.internetbanking.domain.user.Role;
import com.kakaopay.homework.internetbanking.domain.user.RoleType;
import com.kakaopay.homework.internetbanking.domain.user.User;
import com.kakaopay.homework.internetbanking.payload.LoginRequest;
import com.kakaopay.homework.internetbanking.payload.SignupRequest;
import com.kakaopay.homework.internetbanking.payload.UserResponse;
import com.kakaopay.homework.internetbanking.repository.user.RoleRepository;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import com.kakaopay.homework.internetbanking.service.user.UserServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class UserServiceTest extends MockTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void 회원가입_테스트() throws Exception {
        //given
        final SignupRequest signupRequest = SignupRequest.builder()
                .userId("holeman79")
                .password("12345")
                .build();

        final Role role = new Role();
        role.setId(1L);
        role.setName(RoleType.USER);

        User user = User.builder()
                .userId(signupRequest.getUserId())
                .password(signupRequest.getPassword())
                .createdDate(LocalDateTime.now())
                .role(role)
                .build();

        given(userRepository.existsByUserId(signupRequest.getUserId())).willReturn(false);
        given(roleRepository.findByName(RoleType.USER)).willReturn(Optional.of(role));
        given(userRepository.save(any())).willReturn(user);

        //when
        boolean result = userService.addUser(signupRequest);

        //then
        assertThat(result, is(true));

    }

    @Test
    public void 로그인_테스트() throws Exception {
        //given
        final LoginRequest loginRequest = LoginRequest.builder()
                .userId("holeman79")
                .password("12345")
                .build();

        final Role role = new Role();
        role.setId(1L);
        role.setName(RoleType.USER);

        final User user = User.builder()
                .id(79L)
                .userId(loginRequest.getUserId())
                .password(loginRequest.getPassword())
                .createdDate(LocalDateTime.now())
                .role(role)
                .build();

        final String anyJwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNTY5Mjg1Nzg0LCJleHAiOjE1NjkzNDU3ODR9.zS7ZDXUUAJ1FgY72v-7GNzae5ggFe3SdxqK3axWFcrbOMdTWjT-GArWeKyxGXwgjXB7K9VTPe_H8_lgk1fqRtA";

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId(),
                        loginRequest.getPassword()))).willReturn(authentication);

        given(tokenProvider.generateToken(any())).willReturn(anyJwt);

        //when
        UserResponse result = userService.login(loginRequest);

        //then
        assertThat(result.getId(), is(user.getId()));
        assertThat(result.getUserId(), is(user.getUserId()));
        assertThat(result.getAccessToken(), is(anyJwt));
        assertThat(result.getRole(), is(role));
        assertThat(result.getTokenType(), is("Bearer"));
    }
}
