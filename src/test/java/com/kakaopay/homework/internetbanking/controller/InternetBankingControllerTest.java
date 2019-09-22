package com.kakaopay.homework.internetbanking.controller;

import com.kakaopay.homework.internetbanking.config.security.JwtAuthenticationFilter;
import com.kakaopay.homework.internetbanking.config.security.JwtTokenProvider;
import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.repository.DeviceRepository;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import com.kakaopay.homework.internetbanking.repository.user.UserRepository;
import com.kakaopay.homework.internetbanking.service.InternetBankingService;
import com.kakaopay.homework.internetbanking.service.user.CustomUserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@WebMvcTest(controllers = InternetBankingController.class)
@Import({CustomUserDetailsService.class, JwtTokenProvider.class})
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

//    @Test
//    @WithMockUser
//    public void abcfef(){
//
//    }

    @Test
    @WithMockUser
    public void Device_목록가져오기_테스트() throws Exception {
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

}
