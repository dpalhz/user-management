package com.service.authentication.service;

import com.service.authentication.dto.LoginDeviceDto;
import com.service.authentication.dto.LoginHistoryDto;
import com.service.authentication.dto.UserProfileDto;
import java.util.List;
import java.util.UUID;

public interface UserDataService {
  UserProfileDto getProfile(UUID userId);

  List<LoginHistoryDto> getLoginHistory(UUID userId);

  List<LoginDeviceDto> getDevices(UUID userId);
}
