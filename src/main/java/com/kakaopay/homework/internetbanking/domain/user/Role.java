package com.kakaopay.homework.internetbanking.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private RoleType name;

    public Role() {

    }

    public Role(RoleType name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.getRoleType();
    }
}
