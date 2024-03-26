package com.example.springresilience.agent;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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

    @CircuitBreaker(name = "localAgentCircuitBreaker", fallbackMethod = "fallbackCircuitBreaker")
    public String circuitBreaker() {
        return callApi();
    }

    private String fallback(Throwable throwable) {
        return throwable.getMessage();
    }

    @Retry(name = "callApiRetry", fallbackMethod = "fallbackRetry")
    public String retry() {
        return callApi();
    }

    private String fallbackRetry(Throwable throwable) {
        return throwable.getMessage();
    }

    @Bulkhead(name = "callApiBulkHead", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackBulkHead")
    public String bulkHead() {
        return callApi();
    }

    private String fallbackBulkHead(Throwable throwable) {
        return throwable.getMessage();
    }

    private String callApi() {
        return webClient.mutate()
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
