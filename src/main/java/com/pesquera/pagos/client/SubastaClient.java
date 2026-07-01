package com.pesquera.pagos.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;
@Component
public class SubastaClient {
    private final WebClient webClient;
    public SubastaClient(WebClient.Builder builder, @Value("${app.ms-subastas.url}") String url) {
        this.webClient = builder.baseUrl(url).build();
    }
    public Map obtenerSubastaPorId(Long id) {
        return webClient.get()
                .uri("/api/subastas/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    throw new RuntimeException("Subasta no encontrada con id: " + id);
                })
                .bodyToMono(Map.class)
                .block();
    }
}