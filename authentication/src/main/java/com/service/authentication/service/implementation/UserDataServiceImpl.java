package com.service.authentication.service.implementation;

import com.service.authentication.dto.LoginDeviceDto;
import com.service.authentication.dto.LoginHistoryDto;
import com.service.authentication.dto.UserProfileDto;
import com.service.authentication.entity.UserProfile;
import com.service.authentication.repository.UserDeviceRepository;
import com.service.authentication.repository.LoginHistoryRepository;
import com.service.authentication.repository.ProfileRepository;
import com.service.authentication.service.UserDataService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDataServiceImpl implements UserDataService {

  private final ProfileRepository profileRepo;
  private final LoginHistoryRepository loginHistoryRepo;
  private final UserDeviceRepository loginDeviceRepo;

  @Override
  public UserProfileDto getProfile(UUID userId) {
    UserProfile profile =
        profileRepo
            .findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Profile not found"));
    UserProfileDto dto = new UserProfileDto();
    BeanUtils.copyProperties(profile, dto);
    return dto;
  }

  @Override
  public List<LoginHistoryDto> getLoginHistory(UUID userId) {
    return loginHistoryRepo.findByUserId(userId).stream()
        .map(
            item -> {
              LoginHistoryDto dto = new LoginHistoryDto();
              BeanUtils.copyProperties(item, dto);
              return dto;
            })
        .collect(Collectors.toList());
  }

  @Override
  public List<LoginDeviceDto> getDevices(UUID userId) {
    return loginDeviceRepo.findByUserId(userId).stream()
        .map(
            item -> {
              LoginDeviceDto dto = new LoginDeviceDto();
              BeanUtils.copyProperties(item, dto);
              return dto;
            })
        .collect(Collectors.toList());
  }
}
