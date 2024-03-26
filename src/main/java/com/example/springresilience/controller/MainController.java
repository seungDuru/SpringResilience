package com.example.springresilience.controller;

import com.example.springresilience.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final WebClient webClient;

    @GetMapping("/")
    public String index() {
        return webClient.mutate()
                .build()
                .get()
                .uri("http://localhost:9000/data")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
