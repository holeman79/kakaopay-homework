package com.kakaopay.homework.internetbanking.repository.user;

import com.kakaopay.homework.internetbanking.domain.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);
    Boolean existsByUserId(String userId);
    @Cacheable(value = "userCache")
    Optional<User> findById(Long id);
}
