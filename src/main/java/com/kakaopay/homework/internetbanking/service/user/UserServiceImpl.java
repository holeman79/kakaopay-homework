package com.kakaopay.homework.internetbanking.service.user;

import com.kakaopay.homework.internetbanking.config.security.JwtTokenProvider;
import com.kakaopay.homework.internetbanking.domain.user.RoleType;
import com.kakaopay.homework.internetbanking.domain.user.User;
import com.kakaopay.homework.internetbanking.exception.BusinessException;
import com.kakaopay.homework.internetbanking.exception.ErrorCode;
import com.kakaopay.homework.internetbanking.exception.UserDuplException;
import com.kakaopay.homework.internetbanking.payload.LoginRequest;
import com.kakaopay.homework.internetbanking.payload.SignupRequest;
import com.kakaopay.homework.internetbanking.payload.UserResponse;
import com.kakaopay.homework.internetbanking.repository.user.RoleRepository;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder;

    public UserResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String accessToken = tokenProvider.generateToken(user.getId());
        log.info("token: {}", accessToken);

        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .accessToken(accessToken)
                .role(user.getRole())
                .build();
    }

    public boolean addUser(SignupRequest signupRequest) {
        if(userRepository.existsByUserId(signupRequest.getUserId()))
            throw new UserDuplException(signupRequest.getUserId());

        // Creating user's account
        User user = User.builder()
                .userId(signupRequest.getUserId())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .createdDate(LocalDateTime.now())
                .role(roleRepository.findByName(RoleType.USER).orElse(null))
                .build();

        User result = userRepository.save(user);

        String accessToken = tokenProvider.generateToken(Optional.ofNullable(result).map(User::getId).orElse(null));
        log.info("token: {}", accessToken);
        return (result != null);
    }

    public String refreshToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer Token")) throw new BusinessException(ErrorCode.INVALID_AUTHORIZATION_HEADER);

        bearerToken = bearerToken.substring(13);
        if(!tokenProvider.validateToken(bearerToken)) throw new BusinessException(ErrorCode.ACCESS_TOKEN_NOT_VALID);

        bearerToken = tokenProvider.generateToken(tokenProvider.getUserIdFromJWT(bearerToken));
        return new StringBuffer().append("{\"refreshToken\": \"").append(bearerToken).append("\"}").toString();

    }
}
