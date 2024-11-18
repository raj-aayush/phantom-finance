package com.rajaayush.api.controller;

import com.rajaayush.api.dto.AuthRequest;
import com.rajaayush.api.entity.AuthToken;
import com.rajaayush.api.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/")
    public void register(@RequestBody AuthRequest request) throws BadRequestException {
        authService.register(request.getUsername(), request.getPassword());
    }

    @PostMapping("/login/")
    public String login(@RequestBody AuthRequest request) throws BadRequestException {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("/logout/")
    public void logout(HttpServletRequest request) throws BadRequestException {
        authService.logout(AuthToken.extract(request));
    }
}
