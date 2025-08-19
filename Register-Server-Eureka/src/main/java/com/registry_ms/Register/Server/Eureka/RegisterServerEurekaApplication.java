package com.registry_ms.Register.Server.Eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegisterServerEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegisterServerEurekaApplication.class, args);
	}

}
