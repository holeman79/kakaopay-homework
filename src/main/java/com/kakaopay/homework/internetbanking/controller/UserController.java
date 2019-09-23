package com.kakaopay.homework.internetbanking.controller;

import com.kakaopay.homework.internetbanking.domain.user.User;
import com.kakaopay.homework.internetbanking.payload.LoginRequest;
import com.kakaopay.homework.internetbanking.payload.SignupRequest;
import com.kakaopay.homework.internetbanking.payload.UserResponse;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import com.kakaopay.homework.internetbanking.service.user.CustomUserDetailsService;
import com.kakaopay.homework.internetbanking.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        if(userService.addUser(signupRequest)) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.METHOD_FAILURE);
    }

    @GetMapping("/refresh/token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request) {
        return new ResponseEntity<>(userService.refreshToken(request), HttpStatus.OK);
    }

}
