package com.service.authentication.dto;

import lombok.Data;
import java.time.LocalDate; 


@Data
public class ProfileDto {
    private String name;
    private String avatarUrl;
    private LocalDate birthDate;
    private String bio;
}