package com.kakaopay.homework.internetbanking.service;

import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InternetBankingServiceTest extends MockTest{

    @InjectMocks
    private InternetBankingServiceImpl internetBankingService;

    @Mock
    private InternetBankingInfoRepository internetBankingInfoRepository;

    @Test
    public void 디바이스ID_입력_인터넷뱅킹_사용율_가장높은_년도_출력테스트() throws Exception{
        //given
        List<InternetBankingInfo> expected = Arrays.asList(
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2011).rate(95.1).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2012).rate(93.9).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2013).rate(67.1).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2014).rate(61.5).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2015).rate(61.9).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2016).rate(58.5).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2017).rate(61.4).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2018).rate(51.2).build()
        );

        given(internetBankingInfoRepository.findByDeviceDeviceIdOrderByYear("DIS285923")).willReturn(expected);

        String inputDeviceId = "DIS285923";
        String inputDeviceName = "데스크탑 컴퓨터";
        int nextYear = expected.get(expected.size()-1).getYear()+1;

        final InternetBankingInfo result = internetBankingService.getPredictionNextYearRateByDeviceId(inputDeviceId);

        assertThat(result).isNotNull();
        assertThat(result.getDevice().getDeviceId(), is(inputDeviceId));
        assertThat(result.getDevice().getDeviceName(), is(inputDeviceName));
        assertThat(result.getYear(), is(nextYear));
        assertThat(result.getRate(), is(42.3));
    }
}
