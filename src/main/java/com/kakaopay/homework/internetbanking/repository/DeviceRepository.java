package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.domain.Device;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {
    @Cacheable("deviceListCache")
    List<Device> findAll();
}
