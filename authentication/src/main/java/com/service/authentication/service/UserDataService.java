package com.service.authentication.service;

import com.service.authentication.dto.LoginDeviceDto;
import com.service.authentication.dto.LoginHistoryDto;
import com.service.authentication.dto.ProfileDto;
import java.util.List;
import java.util.UUID;

public interface UserDataService {
  ProfileDto getProfile(UUID userId);

  List<LoginHistoryDto> getLoginHistory(UUID userId);

  List<LoginDeviceDto> getDevices(UUID userId);
}
