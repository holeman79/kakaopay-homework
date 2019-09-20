package com.kakaopay.homework.internetbanking.controller;

import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import com.kakaopay.homework.internetbanking.repository.DeviceRepository;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import com.kakaopay.homework.internetbanking.service.InternetBankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/internetbanking/info")
public class InternetBankingController {

    private final DeviceRepository deviceRepository;
    private final InternetBankingInfoRepository internetBankingInfoRepository;
    private final InternetBankingService internetBankingService;

    @GetMapping("/device/list")
    public ResponseEntity<List<Device>> getDeviceList(){
        List<Device> result = deviceRepository.findAll();
        return new ResponseEntity<List<Device>>(result, HttpStatus.OK);
    }

    @GetMapping("/year")
    public ResponseEntity<List<InternetBankingInfo>> getTopRateDeviceListByYear(){
        List<InternetBankingInfo> result = internetBankingInfoRepository.getTopRateDeviceLByYear();
        return new ResponseEntity<List<InternetBankingInfo>>(result, HttpStatus.OK);
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<InternetBankingInfo> getTopRateDeviceByYear(@PathVariable int year){
        InternetBankingInfo result = internetBankingInfoRepository.findTop1ByYearOrderByRateDesc(year);
        return new ResponseEntity<InternetBankingInfo>(result, HttpStatus.OK);
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<InternetBankingInfo> getTopRateDeviceByDeviceId(@PathVariable String deviceId){
        InternetBankingInfo result = internetBankingInfoRepository.findTop1ByDeviceDeviceIdOrderByRateDesc(deviceId);
        return new ResponseEntity<InternetBankingInfo>(result, HttpStatus.OK);
    }

    @GetMapping("/prediction/next/year/rate/{deviceId}")
    public ResponseEntity<InternetBankingInfo> getPredictionNextYearRateByDeviceId(@PathVariable String deviceId){
        InternetBankingInfo result = internetBankingService.getPredictionNextYearRateByDeviceId(deviceId);
        return new ResponseEntity<InternetBankingInfo>(result, HttpStatus.OK);
    }

}
