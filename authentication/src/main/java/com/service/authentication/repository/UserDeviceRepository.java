package com.service.authentication.repository;

import com.service.authentication.entity.UserDevice;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID> {
  List<UserDevice> findByUserId(UUID userId);
}
