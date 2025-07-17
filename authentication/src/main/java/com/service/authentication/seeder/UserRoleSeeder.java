package com.service.authentication.seeder;

import com.service.authentication.entity.UserRole;
import com.service.authentication.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserRoleSeeder {

    private final UserRoleRepository userRoleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seedData() {
        List<String> roles = List.of("ADMIN", "REGULAR", "MANAGER", "GUEST");

        for (String roleName : roles) {
            userRoleRepository.findByName(roleName).ifPresentOrElse(
                r -> {},
                () -> {
                    UserRole newRole = UserRole.builder().name(roleName).build();
                    userRoleRepository.save(newRole);
                    System.out.println("Seeded role: " + roleName);
                }
            );
        }
    }
}
