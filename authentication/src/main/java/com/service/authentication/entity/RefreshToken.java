package com.service.authentication.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expiryDate;

    private String deviceInfo;   // optional: user-agent, IP, etc.

    @PrePersist
    public void onCreate() {
        if (this.expiryDate == null) {
            this.expiryDate = Instant.now().plusSeconds(604800); // 7 days
        }
    }
}
