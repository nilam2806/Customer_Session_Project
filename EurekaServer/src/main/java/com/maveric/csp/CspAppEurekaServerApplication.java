package com.maveric.csp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CspAppEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CspAppEurekaServerApplication.class, args);
	}

}
