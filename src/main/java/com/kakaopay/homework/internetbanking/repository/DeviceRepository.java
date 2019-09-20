package com.kakaopay.homework.internetbanking.repository;

import com.kakaopay.homework.internetbanking.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
