package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.config.BeanConfiguration;
import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(BeanConfiguration.class)
public class InternetBankingInfoJpaTest {

    @Autowired
    private InternetBankingInfoRepository internetBankingInfoRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void InternetBankingInfo_저장_테스트(){
        final Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        deviceRepository.save(device);

        final InternetBankingInfo info = InternetBankingInfo.builder().device(device).year(2011).rate(26.3).build();
        final InternetBankingInfo savedInfo = internetBankingInfoRepository.save(info);
        assertThat(savedInfo.getDevice().getDeviceId(), is(info.getDevice().getDeviceId()));
        assertThat(savedInfo.getRate(), is(info.getRate()));
    }

    @Test
    public void InternetBankingInfo_저장하고_검색_테스트(){
        final Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        deviceRepository.save(device);

        final InternetBankingInfo info = InternetBankingInfo.builder().device(device).year(2011).rate(26.3).build();
        internetBankingInfoRepository.save(info);
        final InternetBankingInfo info2 = InternetBankingInfo.builder().device(device).year(2012).rate(33.5).build();
        internetBankingInfoRepository.save(info2);
        final InternetBankingInfo info3 = InternetBankingInfo.builder().device(device).year(2013).rate(64.3).build();
        internetBankingInfoRepository.save(info3);

        List<InternetBankingInfo> internetBankingInfoList = internetBankingInfoRepository.findAll();
        assertThat(internetBankingInfoList, hasSize(3));
    }

    @Test
    public void InternetBankingInfo_저장하고_삭제_테스트(){
        final Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        deviceRepository.save(device);

        final InternetBankingInfo info = InternetBankingInfo.builder().device(device).year(2011).rate(26.3).build();
        internetBankingInfoRepository.save(info);
        final InternetBankingInfo info2 = InternetBankingInfo.builder().device(device).year(2012).rate(33.5).build();
        internetBankingInfoRepository.save(info2);

        internetBankingInfoRepository.deleteAll();
        assertThat(internetBankingInfoRepository.findAll(), IsEmptyCollection.empty());
    }
}
