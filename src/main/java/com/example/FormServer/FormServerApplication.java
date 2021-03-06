package com.example.FormServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.FormServer.selenium.ConvertPostCode;

@SpringBootApplication
public class FormServerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(FormServerApplication.class, args);
//		ConvertPostCode.checking("Unit 39A, Main Savoy Centre, 140 Sauchiehall St, Glasgow G2 3DH, United Kingdom");
		SpringApplicationBuilder builder = new SpringApplicationBuilder(FormServerApplication.class);

		builder.headless(false);

		ConfigurableApplicationContext context = builder.run(args);
	}

	
}
