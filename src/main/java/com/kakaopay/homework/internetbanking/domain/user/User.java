package com.kakaopay.homework.internetbanking.domain.user;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    private String userId;

    @Size(max = 100)
    private String password;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Builder
    public User(Long id, String userId, String password, Role role, LocalDateTime createdDate){
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.createdDate = createdDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        //list.add(new SimpleGrantedAuthority(role.getAuthority()));
        return null;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
