package com.rajaayush.api.service;

import com.rajaayush.api.entity.AppUser;
import com.rajaayush.api.entity.AuthToken;
import com.rajaayush.api.repository.AppUserRepository;
import com.rajaayush.api.repository.AuthTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private AuthTokenRepository authTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private AppUser appUser;
    private String username;
    private String rawPassword;
    private String encodedPassword;

    @BeforeEach
    public void setUp() {
        username = "testuser";
        rawPassword = "password123";
        encodedPassword = "encodedPassword123";

        appUser = new AppUser();
        appUser.setId(UUID.randomUUID());
        appUser.setUsername(username);
        appUser.setPassword(encodedPassword);
        appUser.setRoles("ROLE_USER");
    }

    @Test
    public void testRegister_Success() {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUser);

        authService.register(username, rawPassword);

        ArgumentCaptor<AppUser> userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(userCaptor.capture());

        AppUser savedUser = userCaptor.getValue();
        assertEquals(username, savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals("ROLE_USER", savedUser.getRoles());
    }

    @Test
    public void testRegister_UsernameAlreadyExists() {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(appUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.register(username, rawPassword);
        });

        assertEquals("Username already exists in the system", exception.getMessage());
        verify(appUserRepository, never()).save(any(AppUser.class));
    }

    @Test
    public void testLogin_Success() {
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(appUser);
        when(authTokenRepository.save(any(AuthToken.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String token = authService.login(username, rawPassword);

        assertNotNull(token);
        ArgumentCaptor<AuthToken> tokenCaptor = ArgumentCaptor.forClass(AuthToken.class);
        verify(authTokenRepository).save(tokenCaptor.capture());

        AuthToken savedToken = tokenCaptor.getValue();
        assertEquals(token, savedToken.getToken());
        assertEquals(appUser, savedToken.getUser());
        assertTrue(savedToken.getExpirationTs().isAfter(LocalDateTime.now()));
    }

    @Test
    public void testLogin_InvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authService.login(username, rawPassword);
        });

        assertEquals("Bad credentials", exception.getMessage());
        verify(authTokenRepository, never()).save(any(AuthToken.class));
    }

    @Test
    public void testLogout_Success() {
        String token = UUID.randomUUID().toString();

        authService.logout(token);

        verify(authTokenRepository).deleteByToken(token);
    }

    @Test
    public void testLogout_NullToken() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.logout(null);
        });

        assertEquals("No token provided", exception.getMessage());
        verify(authTokenRepository, never()).deleteByToken(anyString());
    }
}
