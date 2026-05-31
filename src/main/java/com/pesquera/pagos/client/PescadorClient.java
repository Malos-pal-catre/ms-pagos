package com.pesquera.pagos.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@Component
public class PescadorClient {
    private final WebClient webClient;
    public PescadorClient(WebClient.Builder builder, @Value("${app.ms-pescadores.url}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }
    public Map obtenerPescadorPorId(Long id) {
        return webClient.get()
                .uri("/api/pescadores/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    throw new RuntimeException("Pescador no encontrado con id: " + id);
                })
                .bodyToMono(Map.class)
                .block();
    }
}