package com.service.authentication.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRole extends BaseEntity {

  @Id @GeneratedValue private UUID id;

  @Column(nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "userRole", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private Set<UserRoleAssignment> assignments = new HashSet<>();
}
