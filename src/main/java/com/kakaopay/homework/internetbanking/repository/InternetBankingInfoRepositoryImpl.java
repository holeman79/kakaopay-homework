package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import com.kakaopay.homework.internetbanking.domain.QDevice;
import com.kakaopay.homework.internetbanking.domain.QInternetBankingInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import static com.kakaopay.homework.internetbanking.domain.QInternetBankingInfo.internetBankingInfo;

@RequiredArgsConstructor
public class InternetBankingInfoRepositoryImpl implements InternetBankingInfoRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<InternetBankingInfo> getTopRateDeviceLByYear() {
        QInternetBankingInfo internetBankingInfo = QInternetBankingInfo.internetBankingInfo;
        QInternetBankingInfo otherInternetBankingInfo = new QInternetBankingInfo("otherInternetBankingInfo");
        QDevice device = QDevice.device;

        JPQLQuery<InternetBankingInfo> query = queryFactory
                .from(internetBankingInfo)
                .innerJoin(internetBankingInfo.device, device)
                .leftJoin(otherInternetBankingInfo)
                .on(internetBankingInfo.year.eq(otherInternetBankingInfo.year))
                .on(internetBankingInfo.rate.lt(otherInternetBankingInfo.rate))
                .where(otherInternetBankingInfo.device.deviceId.isNull())
                .orderBy(internetBankingInfo.year.asc())
                .select(Projections.constructor(InternetBankingInfo.class,
                        internetBankingInfo.year,
                        internetBankingInfo.device.deviceId,
                        internetBankingInfo.device.deviceName,
                        internetBankingInfo.rate));

        return query.fetch();
    }
}
