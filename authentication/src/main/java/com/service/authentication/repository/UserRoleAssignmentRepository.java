package com.service.authentication.repository;

import com.service.authentication.entity.UserRoleAssignment;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface UserRoleAssignmentRepository extends JpaRepository<UserRoleAssignment, UUID> {

  void deleteById(@NonNull UUID id);
  @Modifying
  @Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
  void softDeleteById(@Param("id") UUID id);
}
