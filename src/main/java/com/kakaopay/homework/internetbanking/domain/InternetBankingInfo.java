package com.kakaopay.homework.internetbanking.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
@IdClass(InternetBankingInfoId.class)
public class InternetBankingInfo implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name="device_id")
    private Device device;

    @Id
    private int year;

    private double rate;

    @Builder
    public InternetBankingInfo(Device device, int year, double rate){
        this.device = device;
        this.year = year;
        this.rate = rate;
    }

    public InternetBankingInfo(int year, String deviceId, String deviceName, double rate){
        this.year = year;
        this.device = Device.builder().deviceId(deviceId).deviceName(deviceName).build();
        this.rate = rate;
    }
}
