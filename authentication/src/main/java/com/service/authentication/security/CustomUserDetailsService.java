package com.service.authentication.security;

import com.service.authentication.entity.User;
import com.service.authentication.entity.UserRoleAssignment;
import com.service.authentication.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findWithRolesByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    List<GrantedAuthority> authorities =
        user.getRoleAssignments().stream()
            .map(UserRoleAssignment::getUserRole)
            .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getName()))
            .collect(Collectors.toList());

    return new org.springframework.security.core.userdetails.User(
        user.getId().toString(),
        user.getPassword(),
        user.isEnabled(), // enabled
        true, // accountNonExpired
        true, // credentialsNonExpired
        true, // accountNonLocked
        authorities);
  }
}
