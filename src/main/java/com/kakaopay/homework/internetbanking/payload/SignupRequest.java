package com.kakaopay.homework.internetbanking.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequest implements Serializable {
    @NotBlank
    @Size(max = 20)
    private String userId;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Builder
    public SignupRequest(String userId, String password){
        this.userId = userId;
        this.password = password;
    }
}
