package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternetBankingInfoRepository extends JpaRepository<InternetBankingInfo, String>, InternetBankingInfoRepositoryCustom {
    InternetBankingInfo findTop1ByYearOrderByRateDesc(int year);
    InternetBankingInfo findTop1ByDeviceDeviceIdOrderByRateDesc(String deviceId);
    List<InternetBankingInfo> findByDeviceDeviceIdOrderByYear(String deviceId);
}
