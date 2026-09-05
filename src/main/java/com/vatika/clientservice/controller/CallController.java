package com.vatika.clientservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class CallController {

    private final RestClient secureApiClient;

    public CallController(RestClient secureApiClient) {
        this.secureApiClient = secureApiClient;
    }

    @GetMapping("/call")
    public String call() {
        return secureApiClient.get()
                .uri("/")
                .retrieve()
                .onStatus(status -> true, (req, res) -> {})
                .toBodilessEntity()
                .getStatusCode()
                .toString();
    }
}
