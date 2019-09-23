package com.kakaopay.homework.internetbanking.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Device implements Serializable {
    @Id
    private String deviceId;

    private String deviceName;

    @Builder
    public Device(String deviceId, String deviceName){
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }
}
