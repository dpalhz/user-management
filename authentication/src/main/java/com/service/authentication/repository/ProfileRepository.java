package com.service.authentication.repository;

import com.service.authentication.entity.Profile;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
  Optional<Profile> findByUserId(UUID userId);
}
// This interface extends JpaRepository to provide CRUD operations for the Profile entity.
// It includes a method to find a profile by the associated user's ID.
