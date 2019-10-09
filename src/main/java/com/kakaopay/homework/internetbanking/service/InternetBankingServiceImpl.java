package com.kakaopay.homework.internetbanking.service;

import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import com.kakaopay.homework.internetbanking.util.LinearRegression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternetBankingServiceImpl implements InternetBankingService{

    private final InternetBankingInfoRepository internetBankingInfoRepository;

    @Override
    public InternetBankingInfo getPredictionNextYearRateByDeviceId(String deviceId) {
        List<InternetBankingInfo> rateListByDevice = internetBankingInfoRepository.findByDeviceDeviceIdOrderByYear(deviceId);

        List<Integer> years = rateListByDevice.stream().map(data -> data.getYear()).collect(Collectors.toList());
        List<Double> rates = rateListByDevice.stream().map(data -> data.getRate()).collect(Collectors.toList());

        int nextYear = 2019;
        String deviceName = null;
        if(rateListByDevice.size() > 0){
            nextYear = years.get(years.size()-1) + 1;
            deviceName = rateListByDevice.get(0).getDevice().getDeviceName();
        }

        Double prediction = LinearRegression.predictForValue(years, rates, nextYear);
        prediction = prediction > 100 ? 100.0 : (prediction < 0 ? 0 : prediction);
        prediction = (double)Math.round(prediction * 10) / 10;
        InternetBankingInfo result = InternetBankingInfo.builder()
                .device(Device.builder().deviceId(deviceId).deviceName(deviceName).build())
                .year(nextYear)
                .rate(prediction)
                .build();
        return result;
    }
}
