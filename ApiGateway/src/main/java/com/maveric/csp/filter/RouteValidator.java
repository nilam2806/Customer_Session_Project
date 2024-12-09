package com.maveric.csp.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of(
	        "/users/**",
	        "/users/login",
	        "/validate",
	        "/eureka"
	);


	public Predicate<ServerHttpRequest> isSecured =
	        request -> {
	            System.out.println("Request URI: " + request.getURI());
	            return openApiEndpoints
	                    .stream()
	                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
	        };

	        
	        
}