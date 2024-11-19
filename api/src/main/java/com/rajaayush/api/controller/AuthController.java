package com.rajaayush.api.controller;

import com.rajaayush.api.dto.AuthRequest;
import com.rajaayush.api.entity.AuthToken;
import com.rajaayush.api.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            authService.register(request.getUsername(), request.getPassword());
            return ResponseEntity.ok("Registration successful.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during registration.");
        }
    }

    @PostMapping("/login/")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during login.");
        }
    }

    @PostMapping("/logout/")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            authService.logout(AuthToken.extract(request));
            return ResponseEntity.ok("Logout successful.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Logout failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred during logout.");
        }
    }
}
