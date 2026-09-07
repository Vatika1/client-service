package com.vatika.clientservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/create")
    @CircuitBreaker(name = "secureApi", fallbackMethod = "createFallback")
    public String create(@RequestHeader("Authorization") String auth, @RequestBody String body) {
        return secureApiClient.post()
                .uri("https://localhost:8443/orders/create")
                .header("Authorization", auth)
                .header("Content-Type", "application/json")
                .body(body)
                .retrieve().body(String.class);
    }

    public String createFallback(String auth, String body, Throwable t) {
        return "fallback: " + t.getClass().getSimpleName();
    }
}