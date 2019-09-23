package com.kakaopay.homework.internetbanking.service.user;

import com.kakaopay.homework.internetbanking.domain.user.User;
import com.kakaopay.homework.internetbanking.payload.LoginRequest;
import com.kakaopay.homework.internetbanking.payload.SignupRequest;
import com.kakaopay.homework.internetbanking.payload.UserResponse;

import javax.servlet.http.HttpServletRequest;


public interface UserService {
    boolean addUser(SignupRequest signupRequest);
    UserResponse login(LoginRequest loginRequest);
    String refreshToken(HttpServletRequest request);
}
