package BitProject.Greener.service;


import BitProject.Greener.domain.dto.ChatbotDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;

@Service
@Log4j2
public class WebClientService {
    private final WebClient webClient;

    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    public Flux<ChatbotDTO> result(ChatbotDTO chatbotDTO){
        return this.webClient
                .post()
                .uri(String.format("/user"))
                .retrieve()
                .bodyToFlux(ChatbotDTO.class);
    }
    public String create(ChatbotDTO chatbotDTO) {
        AtomicReference<String> a =null;
        Mono<ChatbotDTO> result = webClient
                .post().uri(String.format("/user"))
                .bodyValue(chatbotDTO)
                .retrieve()
                .bodyToMono(ChatbotDTO.class);


        return result.block().getUser_input();

    }

}
