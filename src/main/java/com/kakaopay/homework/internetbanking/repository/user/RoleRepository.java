package com.kakaopay.homework.internetbanking.repository.user;

import com.kakaopay.homework.internetbanking.domain.user.Role;
import com.kakaopay.homework.internetbanking.domain.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType roleType);
}
