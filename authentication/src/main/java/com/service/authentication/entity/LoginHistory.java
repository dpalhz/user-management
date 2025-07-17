package com.service.authentication.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;  
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;    
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "login_histories") // atau nama tabel kamu
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistory {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Instant loginTime;
    private String ipAddress;
    private String location;
    private String deviceName;
    private boolean success;
}
