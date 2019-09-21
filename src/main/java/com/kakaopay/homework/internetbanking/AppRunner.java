package com.kakaopay.homework.internetbanking;

import com.kakaopay.homework.internetbanking.domain.Device;
import com.kakaopay.homework.internetbanking.domain.InternetBankingInfo;
import com.kakaopay.homework.internetbanking.domain.user.Role;
import com.kakaopay.homework.internetbanking.domain.user.RoleType;
import com.kakaopay.homework.internetbanking.repository.DeviceRepository;
import com.kakaopay.homework.internetbanking.repository.InternetBankingInfoRepository;
import com.kakaopay.homework.internetbanking.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class AppRunner implements ApplicationRunner {
    private static String fileName = "2019_using_internet_rate.csv";
    private List<String> deviceNameList;
    private List<List<String>> content = new ArrayList<>();

    private final DeviceRepository deviceRepository;
    private final InternetBankingInfoRepository internetBankingInfoRepository;
    private final RoleRepository roleRepository;


    public void loadFile() {
        try {
            File csv = new File("/home/holeman/Desktop/kakaopay_hw/" + fileName);
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            if((line = br.readLine()) != null) {
                line = line.substring(line.indexOf(',', line.indexOf(',')+1)+1);
                deviceNameList = Arrays.asList(line.split(","));
            }
            while((line = br.readLine()) != null)
                content.add(Arrays.asList(line.split(",")));

        } catch (IOException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    public void saveData() throws Exception{

        List<String> uniqueDeviceIdList = getUniqueDeviceIdList(6, deviceNameList.size());

        for(int i = 0; i < uniqueDeviceIdList.size(); i++){
            String deviceId = uniqueDeviceIdList.get(i);
            String deviceName = deviceNameList.get(i);
            Device device = new Device();
            device.setDeviceId(deviceId);
            device.setDeviceName(deviceName);
            deviceRepository.save(Device.builder()
                    .deviceId(deviceId)
                    .deviceName(deviceName)
                    .build());
            for(int j = 0; j < content.size(); j++){
                List<String> data = content.get(j);
                int year = Integer.parseInt(data.get(0));
                String strRate = data.get(2+i);
                if(strRate.equals("-")) strRate = "0";
                double rate = Double.parseDouble(strRate);

                internetBankingInfoRepository.save(InternetBankingInfo.builder()
                        .device(Device.builder().deviceId(deviceId).deviceName(deviceName).build())
                        .year(year)
                        .rate(rate)
                        .build());
            }
        }

        Role userRole = new Role(RoleType.USER);
        Role adminRole = new Role(RoleType.ADMIN);
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
    }

    public List getUniqueDeviceIdList(int numberLength, int numberCount) throws Exception{
        if(Math.pow(10, numberLength) < numberCount) throw new Exception("중복 허용되지않아 lineNumber를 더 크게 지정해주세요");
        Set<String> uniqueDeviceIdList = new HashSet<String>();
        for(int i=0; i < numberCount; i++) {
            String numStr = getRandomNumString(numberLength);
            if (!uniqueDeviceIdList.add(numStr)) i--;
        }
        return new ArrayList(uniqueDeviceIdList);
    }

    public static String getRandomNumString(int size){
        String result = new String();
        for(int i=0; i<size; i++) result += new Random().nextInt(10);
        return "DIS" + result;
    }

    public void checkData(){
        List<Device> deviceList = deviceRepository.findAll();
        List<InternetBankingInfo> internetBankingInfoList = internetBankingInfoRepository.findAll();

        deviceList.stream().forEach(s -> System.out.println("DeivceInfo: " + s.getDeviceId() + " : " + s.getDeviceName()));
        internetBankingInfoList.stream().forEach(s -> System.out.println("InternetRate: "+ s.toString()));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //String[] arguments = args.getSourceArgs();
        //if(arguments.length > 0)
        loadFile();
        saveData();
    }
}
