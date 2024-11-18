package com.rajaayush.api.service;

import com.rajaayush.api.entity.AppUser;
import com.rajaayush.api.repository.AppUserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(String username, String password) throws BadRequestException {
        if(appUserRepository.findByUsername(username).isPresent()) {
            throw new BadRequestException("Username already exists in the system");
        }
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUserRepository.save(appUser);
    }
}
