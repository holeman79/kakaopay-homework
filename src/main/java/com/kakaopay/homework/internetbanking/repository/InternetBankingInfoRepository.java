package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InternetBankingInfoRepository extends JpaRepository<InternetBankingInfo, String>, InternetBankingInfoRepositoryCustom {
    @Cacheable(value = "internetBankingInfoCache", key="#p0")
    InternetBankingInfo findTop1ByYearOrderByRateDesc(int year);
    @Cacheable(value = "internetBankingInfoCache", key="#p0")
    InternetBankingInfo findTop1ByDeviceDeviceIdOrderByRateDesc(String deviceId);
    List<InternetBankingInfo> findByDeviceDeviceIdOrderByYear(String deviceId);
}
