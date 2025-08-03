package com.service.authentication.repository;

import com.service.authentication.entity.User;
import com.service.authentication.entity.UserProfile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<UserProfile, UUID> {
  Optional<UserProfile> findByUserId(UUID userId);

  Optional<UserProfile> findByUser(User user);
}
// This interface extends JpaRepository to provide CRUD operations for the Profile entity.
// It includes a method to find a profile by the associated user's ID.
