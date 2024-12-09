package com.maveric.csp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CspAppApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CspAppApiGatewayApplication.class, args);
	}
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
	    return builder
	            .routes()
	            .route(r -> r.path("api/v1/user-service/**").uri("http://localhost:8084"))
	            .build();
	}

}
