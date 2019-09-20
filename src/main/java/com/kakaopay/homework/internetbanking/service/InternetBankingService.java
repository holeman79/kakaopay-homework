package com.kakaopay.homework.internetbanking.service;

import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
public interface InternetBankingService {
    InternetBankingInfo getPredictionNextYearRateByDeviceId(String deviceId);
}
