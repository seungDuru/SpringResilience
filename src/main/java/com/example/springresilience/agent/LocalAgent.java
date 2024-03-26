package com.example.springresilience.agent;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Component
public class LocalAgent {

    @Value("${agent.uri}")
    private String uri;

    private final WebClient webClient;

//    @CircuitBreaker(name = "localAgentCircuitBreaker", fallbackMethod = "fallback")
    @Retry(name = "localAgentRetry", fallbackMethod = "fallbackRetry")
    public String callApi() {
        return webClient.mutate()
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String fallback(Throwable throwable) {
        return throwable.getMessage();
    }

    private String fallbackRetry(Throwable throwable) {
        return throwable.getMessage();
    }

}
