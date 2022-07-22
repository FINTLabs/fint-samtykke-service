package no.fintlabs.fint;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class FintClient {
    private final WebClient webClient;

    private final Map<String, Long> sinceTimestamp = new ConcurrentHashMap<>();

    public FintClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<List<Object>> getResourcesLastUpdated(String endpoint) {
        return getLastUpdated(endpoint)
                .flatMapIterable(ObjectResources::getContent)
                .collect(Collectors.toList());
    }

    public void resetLastUpdatedTimestamps() {
        this.sinceTimestamp.clear();
    }

    private Mono<ObjectResources> getLastUpdated(String endpoint) {
        return webClient.get()
                .uri(endpoint.concat("/last-updated"))
                .retrieve()
                .bodyToMono(LastUpdated.class)
                .flatMap(lastUpdated -> webClient.get()
                        .uri(endpoint, uriBuilder -> uriBuilder.queryParam("sinceTimeStamp", sinceTimestamp.getOrDefault(endpoint, 0L)).build())
                        .retrieve()
                        .bodyToMono(ObjectResources.class)
                        .doOnNext(it -> sinceTimestamp.put(endpoint, lastUpdated.getLastUpdated()))
                );
    }

    public Mono<Object> getResource(String endpoint) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(Object.class);
    }

    public <T> Mono<T> getResource(String endpoint, Class<T> clazz) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(clazz);
    }

    public <K, T> Mono<K> postResource(String url, T request, Class<K> clazz){
        return webClient
                .post()
                .uri(url)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(clazz);
    }

    public <K,T> Mono<ResponseEntity<Void>> postRes(String url, T request,Class<K> clazz){
        return webClient.post()
                .uri(url)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .toBodilessEntity();
    }
    public <K,T> Mono<ResponseEntity<Void>> putResource(String url, T request, Class<K> clazz){
        return webClient.put()
                .uri(url)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .toBodilessEntity();
    }

//    public <T> Mono<T> getStatus(String url, Class<T> clazz){
//        return webClient
//                .get()
//                .uri(loc)
//    }


    @Data
    private static class LastUpdated {
        private Long lastUpdated;
    }

    public void reset() {
        sinceTimestamp.clear();
    }
}