package com.kakaopay.homework.internetbanking.controller;

import com.kakaopay.homework.internetbanking.config.BeanConfiguration;
import com.kakaopay.homework.internetbanking.config.CacheConfiguration;
import com.kakaopay.homework.internetbanking.config.security.JwtAuthenticationFilter;
import com.kakaopay.homework.internetbanking.config.security.JwtTokenProvider;
import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import com.kakaopay.homework.internetbanking.repository.DeviceRepository;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import com.kakaopay.homework.internetbanking.service.InternetBankingService;
import com.kakaopay.homework.internetbanking.service.user.CustomUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(InternetBankingController.class)
@Import({ JwtTokenProvider.class, CustomUserDetailsService.class, CacheConfiguration.class})
public class InternetBankingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeviceRepository deviceRepository;

    @MockBean
    private InternetBankingInfoRepository internetBankingInfoRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private InternetBankingService internetBankingService;




    @Test
    public void 시큐리티_인증되지_않을경우_권한없음_상태_출력테스트() throws Exception{
        final ResultActions actions = mvc.perform(get("/api/internetbanking/info/year/{year}", 2014)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void 디바이스_전체_리스트_출력테스트() throws Exception {
        //given
        List<Device> expected = Arrays.asList(
                Device.builder().deviceId("DIS723645").deviceName("스마트폰").build(),
                Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build(),
                Device.builder().deviceId("DIS583829").deviceName("노트북 컴퓨터").build(),
                Device.builder().deviceId("DIS195834").deviceName("기타").build(),
                Device.builder().deviceId("DIS049378").deviceName("스마트패드").build()
        );
        given(deviceRepository.findAll()).willReturn(expected);

        //when
        final ResultActions actions = mvc.perform(get("/api/internetbanking/info/device/list")
                                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                                        .andDo(print());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].deviceId", is("DIS723645")))
                .andExpect(jsonPath("$[0].deviceName").value("스마트폰"));
    }

    @Test
    @WithMockUser
    public void 년도별_인터넷뱅킹_사용율_가장높은_디바이스_리스트_출력테스트() throws Exception{
        //given
        List<InternetBankingInfo> expected = Arrays.asList(
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2011).rate(95.1).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2012).rate(93.9).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS285923").deviceName("데스크탑 컴퓨터").build()).year(2013).rate(67.1).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS723645").deviceName("스마트폰").build()).year(2014).rate(64.2).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS723645").deviceName("스마트폰").build()).year(2015).rate(73.2).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS723645").deviceName("스마트폰").build()).year(2016).rate(85.1).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS723645").deviceName("스마트폰").build()).year(2017).rate(90.6).build(),
                InternetBankingInfo.builder().device(Device.builder().deviceId("DIS723645").deviceName("스마트폰").build()).year(2018).rate(90.5).build()
        );
        given(internetBankingInfoRepository.getTopRateDeviceLByYear()).willReturn(expected);

        //when
        final ResultActions actions = mvc.perform(get("/api/internetbanking/info/year")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)))
                .andExpect(jsonPath("$[0].device.deviceId").value("DIS285923"))
                .andExpect(jsonPath("$[0].device.deviceName").value("데스크탑 컴퓨터"))
                .andExpect(jsonPath("$[0].year").value("2011"))
                .andExpect(jsonPath("$[0].rate").value("95.1"));

    }

    @Test
    @WithMockUser
    public void 년도_입력_인터넷뱅킹_사용율_가장높은_디바이스_출력테스트() throws Exception{
        //given
        InternetBankingInfo expected = InternetBankingInfo.builder().device(Device.builder().deviceId("DIS723645").deviceName("스마트폰").build()).year(2014).rate(64.2).build();
        given(internetBankingInfoRepository.findTop1ByYearOrderByRateDesc(2014)).willReturn(expected);

        //when
        final ResultActions actions = mvc.perform(get("/api/internetbanking/info/year/{year}", 2014)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.device.deviceId").value("DIS723645"))
                .andExpect(jsonPath("$.device.deviceName").value("스마트폰"))
                .andExpect(jsonPath("$.year").value(2014))
                .andExpect(jsonPath("$.rate").value("64.2"));
    }

    @Test
    @WithMockUser
    public void 디바이스ID_입력_인터넷뱅킹_사용율_가장높은_년도_출력테스트() throws Exception{
        //given
        InternetBankingInfo expected = InternetBankingInfo.builder().device(Device.builder().deviceId("DIS376298").deviceName("스마트패드").build()).year(2018).rate(3.3).build();
        given(internetBankingInfoRepository.findTop1ByDeviceDeviceIdOrderByRateDesc("DIS376298")).willReturn(expected);

        //when
        final ResultActions actions = mvc.perform(get("/api/internetbanking/info/device/{deviceId}", "DIS376298")
                .param("deviceId", "DIS376298")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.device.deviceId").value("DIS376298"))
                .andExpect(jsonPath("$.device.deviceName").value("스마트패드"))
                .andExpect(jsonPath("$.year").value(2018))
                .andExpect(jsonPath("$.rate").value(3.3));
    }

    @Test
    @WithMockUser
    public void 다음년도_인터넷뱅킹_사용율_예측값_출력테스트() throws Exception{
        //given
        InternetBankingInfo expected = InternetBankingInfo.builder().device(Device.builder().deviceId("DIS648363").deviceName("노트북 컴퓨터").build()).year(2019).rate(15.3).build();
        given(internetBankingService.getPredictionNextYearRateByDeviceId("DIS648363")).willReturn(expected);

        //when
        final ResultActions actions = mvc.perform(get("/api/internetbanking/info/prediction/next/year/rate/{deviceId}", "DIS648363")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.device.deviceId").value("DIS648363"))
                .andExpect(jsonPath("$.device.deviceName").value("노트북 컴퓨터"))
                .andExpect(jsonPath("$.year").value(2019))
                .andExpect(jsonPath("$.rate").value(15.3));
    }

}
