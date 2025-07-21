package com.service.authentication.repository;

import com.service.authentication.entity.LoginHistory;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, UUID> {
  List<LoginHistory> findByUserId(UUID userId);
}
