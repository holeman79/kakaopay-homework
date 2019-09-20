package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import java.util.List;

public interface InternetBankingInfoRepositoryCustom{
    List<InternetBankingInfo> getTopRateDeviceLByYear();
}
