package com.amaresh.devcollege;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DevcollegeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevcollegeApplication.class, args);
	}
	
	@Bean
    ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
