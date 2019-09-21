package com.kakaopay.homework.internetbanking.payload;

import com.kakaopay.homework.internetbanking.domain.user.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private Long id;
    private String userId;
    private String accessToken;
    private String tokenType = "Bearer";
    private Role role;

    @Builder
    public UserResponse(Long id, String userId, String name, String imageUrl, String accessToken, Role role){
        this.id = id;
        this.userId = userId;
        this.accessToken = accessToken;
        this.role = role;
    }
}
