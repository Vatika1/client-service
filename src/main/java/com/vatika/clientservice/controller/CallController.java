package com.vatika.clientservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class CallController {

    private final RestClient secureApiClient;

    public CallController(RestClient secureApiClient) {
        this.secureApiClient = secureApiClient;
    }

    @GetMapping("/call")
    @CircuitBreaker(name = "secureApi", fallbackMethod = "fallback")
    public String call(@RequestHeader("Authorization") String auth) {
        return secureApiClient.get()
                .uri("https://localhost:8443/orders")
                .header("Authorization", auth)
                .retrieve().body(String.class);
    }

    public String fallback(String auth, Throwable t) {
        return "fallback: " + t.getClass().getSimpleName();
    }
}