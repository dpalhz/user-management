package com.service.authentication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_role_assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleAssignment {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole userRole;

    @Column(nullable = false, updatable = false)
    private Instant grantedAt;

    @Column
    private Instant expiredAt;

    @Column
    private String grantedBy; // optional: who granted the role, e.g., ID

    @PrePersist
    public void onCreate() {
        this.grantedAt = Instant.now();
    }
}
