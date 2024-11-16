package com.rajaayush.api;

import com.rajaayush.api.entity.Profile;
import com.rajaayush.api.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Autowired
	private ProfileRepository profileRepository;

	@GetMapping("/api/hello")
	public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
		Profile p = new Profile();
		p.setFirstName(name);
		p.setEmail(name+"Dummy@mail.com");
		p = profileRepository.save(p);
		return String.format("Hello %s!", p.getFirstName());
	}
}
