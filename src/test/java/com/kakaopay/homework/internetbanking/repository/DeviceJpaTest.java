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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(BeanConfiguration.class)
public class DeviceJpaTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void Device_저장_테스트(){
        final Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        final Device savedDevice = deviceRepository.save(device);

        assertThat(savedDevice.getDeviceId(), is(device.getDeviceId()));
        assertThat(savedDevice.getDeviceName(), is(device.getDeviceName()));
    }

    @Test
    public void Device_저장하고_검색_테스트(){
        Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        deviceRepository.save(device);
        device = Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build();
        deviceRepository.save(device);
        device = Device.builder().deviceId("DIS583829").deviceName("노트북 컴퓨터").build();
        deviceRepository.save(device);
        device = Device.builder().deviceId("DIS195834").deviceName("기타").build();
        deviceRepository.save(device);
        device = Device.builder().deviceId("DIS049378").deviceName("스마트패드").build();
        deviceRepository.save(device);

        List<Device> DeviceList = deviceRepository.findAll();
        assertThat(DeviceList, hasSize(5));
    }

    @Test
    public void Device_저장하고_삭제_테스트(){
        Device device = Device.builder().deviceId("DIS723645").deviceName("스마트폰").build();
        Device savedDevice1 = deviceRepository.save(device);
        device = Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build();
        Device savedDevice2 = deviceRepository.save(device);

        deviceRepository.delete(savedDevice2);
        assertThat(deviceRepository.findAll(), hasSize(1));
    }
}
