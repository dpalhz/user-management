package com.service.authentication.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;    
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "login_devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDevice {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String deviceName;
    private String ipAddress;
    private String location;
    private String userAgent;
    private Instant lastLoginAt;

    @Column(updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }
}