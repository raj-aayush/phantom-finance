package com.rajaayush.api.controller;

import com.rajaayush.api.dto.UserRegistrationRequest;
import com.rajaayush.api.service.AppUserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @PostMapping("/register/")
    public void register(@RequestBody UserRegistrationRequest request) throws BadRequestException {
        appUserService.register(request.getUsername(), request.getPassword());
    }
}
