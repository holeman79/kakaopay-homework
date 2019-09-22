package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.config.QueryDslConfiguration;
import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(QueryDslConfiguration.class)
public class InternetBankingInfoJpaTest {

    @Autowired
    private InternetBankingInfoRepository internetBankingInfoRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void InternetBankingInfo_엔티티_저장_테스트(){
        final Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        deviceRepository.save(device);

        final InternetBankingInfo info = InternetBankingInfo.builder().device(device).year(2011).rate(26.3).build();
        final InternetBankingInfo savedInfo = internetBankingInfoRepository.save(info);
        assertThat(savedInfo.getDevice().getDeviceId(), is(info.getDevice().getDeviceId()));
        assertThat(savedInfo.getRate(), is(info.getRate()));
    }
}
