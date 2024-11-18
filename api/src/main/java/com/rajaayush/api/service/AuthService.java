package com.rajaayush.api.service;

import com.rajaayush.api.entity.AppUser;
import com.rajaayush.api.entity.AuthToken;
import com.rajaayush.api.repository.AppUserRepository;
import com.rajaayush.api.repository.AuthTokenRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void register(String username, String password) throws BadRequestException {
        if(appUserRepository.findByUsername(username).isPresent()) {
            throw new BadRequestException("Username already exists in the system");
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(passwordEncoder.encode(password));
        appUser.setRoles("ROLE_USER");
        appUserRepository.save(appUser);
    }

    public String login(String username, String password) throws BadRequestException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String token = UUID.randomUUID().toString();

        AppUser appUser = (AppUser) authentication.getPrincipal();

        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUser(appUser);
        authToken.setExpirationTs(LocalDateTime.now().plusWeeks(1));

        authTokenRepository.save(authToken);

        return token;
    }
}
