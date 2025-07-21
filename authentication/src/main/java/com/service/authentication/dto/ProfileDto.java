package com.service.authentication.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ProfileDto {
  private String name;
  private String avatarUrl;
  private LocalDate birthDate;
  private String bio;
}
