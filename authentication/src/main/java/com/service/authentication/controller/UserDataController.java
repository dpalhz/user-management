package com.service.authentication.controller;
import com.service.authentication.dto.LoginDeviceDto;
import com.service.authentication.dto.LoginHistoryDto;
import com.service.authentication.dto.ProfileDto;   
import com.service.authentication.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserDataController {

    private final UserDataService userDataService;

    /**
     * Returns the profile data associated with the given user ID
     * @param userId the user ID
     * @return the profile data
     */
    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(@RequestParam UUID userId) {
        return ResponseEntity.ok(userDataService.getProfile(userId));
    }

    /**
     * Returns a list of login history records for the given user ID
     * @param userId the user ID
     * @return a list of login history records
     */
    @GetMapping("/login-history")
    public ResponseEntity<List<LoginHistoryDto>> getLoginHistory(@RequestParam UUID userId) {
        return ResponseEntity.ok(userDataService.getLoginHistory(userId));
    }

    /**
     * Returns a list of login devices associated with the given user ID
     * @param userId the user ID
     * @return a list of login devices
     */
    @GetMapping("/devices")
    public ResponseEntity<List<LoginDeviceDto>> getDevices(@RequestParam UUID userId) {
        return ResponseEntity.ok(userDataService.getDevices(userId));
    }
}
