package com.kakaopay.homework.internetbanking.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class InternetBankingInfoId implements Serializable {
    private String device;
    private int year;

    @Builder
    public InternetBankingInfoId(String device, int year){
        this.device = device;
        this.year = year;
    }
}
