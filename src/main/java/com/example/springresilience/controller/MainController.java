package com.example.springresilience.controller;

import com.example.springresilience.agent.LocalAgent;
import com.example.springresilience.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final LocalAgent localAgent;

    @GetMapping("/circuit-breaker")
    public String circuitBreaker() {
        return localAgent.circuitBreaker();
    }

    @GetMapping("/retry")
    public String retry() {
        return localAgent.retry();
    }

    @GetMapping("/bulk-head")
    public String bulkHead() {
        return localAgent.bulkHead();
    }
}
