package com.service.authentication.repository;

import com.service.authentication.entity.LoginDevice;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginDeviceRepository extends JpaRepository<LoginDevice, UUID> {
  List<LoginDevice> findByUserId(UUID userId);
}
