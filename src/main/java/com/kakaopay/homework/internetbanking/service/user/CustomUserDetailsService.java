package com.kakaopay.homework.internetbanking.service.user;

import com.kakaopay.homework.internetbanking.config.security.JwtTokenProvider;
import com.kakaopay.homework.internetbanking.domain.user.RoleType;
import com.kakaopay.homework.internetbanking.domain.user.User;
import com.kakaopay.homework.internetbanking.exception.UserDuplException;
import com.kakaopay.homework.internetbanking.exception.UserNotFoundException;
import com.kakaopay.homework.internetbanking.repository.user.RoleRepository;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("userId : " + userId));
        return user;
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("id : " + id));
        return user;
    }
}
