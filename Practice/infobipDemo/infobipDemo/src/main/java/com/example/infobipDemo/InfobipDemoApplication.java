package com.example.infobipDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class InfobipDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfobipDemoApplication.class, args);
	}

}
