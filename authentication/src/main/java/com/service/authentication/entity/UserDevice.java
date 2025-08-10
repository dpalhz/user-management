package com.service.authentication.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "login_devices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDevice extends BaseEntity {
  @Id @GeneratedValue private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  private String deviceType;
  private String osName;
  private String ipAddress;
  private String location;
  private String userAgent;
  private Instant lastActivityAt;


 
}
