package com.service.authentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;


import java.time.Instant;
import java.util.UUID;



@Entity
@Table(name = "user_role_assignments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRoleAssignment extends BaseEntity {    

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private UserRole userRole;

    @Column
    private Instant expiredAt;

    @JoinColumn(name = "granted_by")
    @ManyToOne(fetch = FetchType.LAZY)
    private User grantedBy;

}
