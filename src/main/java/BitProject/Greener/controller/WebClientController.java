package BitProject.Greener.controller;

import BitProject.Greener.domain.dto.ChatbotDTO;
import BitProject.Greener.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Log4j2
public class WebClientController {
    private final WebClientService webClientService;
    @PostMapping("/user")
    public ResponseEntity doTest(@RequestBody ChatbotDTO chatbotDTO) {

//        webClientService.result(chatbotDTO);
        String a = webClientService.create(chatbotDTO);
        return ResponseEntity.ok().body(a);

    }
}