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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        String accessToken = tokenProvider.generateToken(1L);

        User user = User.builder()
                .id(1L)
                .userId(signupRequest.getUserId())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .createdDate(LocalDateTime.now())
                .role(roleRepository.findByName(RoleType.USER).orElse(null))
                .build();

        given(userRepository.existsByUserId(any())).willReturn(false);
        given(roleRepository.findByName(RoleType.USER)).willReturn(Optional.of(role));
        given(userRepository.save(user)).willReturn(user);
        given(passwordEncoder.encode(signupRequest.getPassword())).willReturn(encodedPassword);
        given(tokenProvider.generateToken(1L)).willReturn(accessToken);

        //when
        boolean result = userService.addUser(signupRequest);

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);

    }

    @Test
    public void 로그인_테스트() throws Exception {

        final SignupRequest signupRequest = SignupRequest.builder()
                .userId("holeman79")
                .password("12345")
                .build();

        final Role role = new Role();
        role.setId(1L);
        role.setName(RoleType.USER);

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        given(userRepository.existsByUserId(any())).willReturn(false);
        given(roleRepository.findByName(RoleType.USER)).willReturn(Optional.of(role));
        given(passwordEncoder.encode(signupRequest.getPassword())).willReturn(encodedPassword);

        boolean result = userService.addUser(signupRequest);

        final LoginRequest loginRequest = LoginRequest.builder()
                .userId("holeman79")
                .password("12345")
                .build();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(),
                loginRequest.getPassword()));

        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(),
                                                    loginRequest.getPassword())))
                .willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(User.builder()
                .userId(loginRequest.getUserId())
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .createdDate(LocalDateTime.now())
                .role(role)
                .build());

    }

}
