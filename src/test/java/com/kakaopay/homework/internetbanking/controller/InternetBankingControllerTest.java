package com.kakaopay.homework.internetbanking.controller;

import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.repository.DeviceRepository;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import com.kakaopay.homework.internetbanking.service.InternetBankingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(InternetBankingController.class)
public class InternetBankingControllerTest {

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private InternetBankingInfoRepository internetBankingInfoRepository;

    @MockBean
    private InternetBankingService internetBankingService;

    @Test
    public void Device_목록가져오기_테스트() throws Exception {
        //given
        Device device = Device.builder()
                        .deviceId("DIS723645")
                        .deviceName("스마트폰").build();

    }

}
