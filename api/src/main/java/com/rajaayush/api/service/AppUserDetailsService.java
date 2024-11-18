package com.rajaayush.api.service;

import com.rajaayush.api.entity.AppUser;
import com.rajaayush.api.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUserQuery = appUserRepository.findByUsername(username);
        if(appUserQuery.isPresent()) {
            AppUser appUser = appUserQuery.get();
            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword())
                    .roles(appUser.getRoles().split(","))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
