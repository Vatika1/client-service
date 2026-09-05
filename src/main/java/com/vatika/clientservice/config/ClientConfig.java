package com.vatika.clientservice.config;

import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient secureApiClient(SslBundles sslBundles) {
        SslBundle bundle = sslBundles.getBundle("secure-api");
        HttpClient httpClient = HttpClient.newBuilder()
                .sslContext(bundle.createSslContext())
                .build();
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .baseUrl("https://localhost:8443")
                .build();
    }
}
