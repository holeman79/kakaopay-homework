package com.kakaopay.homework.internetbanking.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Size(max = 20)
    private String userId;

    @NotBlank
    @Size(max = 100)
    private String password;

}
