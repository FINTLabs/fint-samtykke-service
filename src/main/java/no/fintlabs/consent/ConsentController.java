package no.fintlabs.consent;

import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.personvern.samtykke.SamtykkeResources;
import no.vigoiks.resourceserver.security.FintJwtEndUserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api")
//TODO: rearrange endpoints to match prod uri
public class ConsentController {
    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

//    @GetMapping("/consents")
//    public Mono<SamtykkeResources> getConsents(@AuthenticationPrincipal Jwt jwt) throws ExecutionException, InterruptedException {
//        return consentService.getFilteredConsents(FintJwtEndUserPrincipal.from(jwt));
//    }

    @GetMapping("/consents")
    public Mono<List<ApiConsent>> getApiConsents(@AuthenticationPrincipal Jwt jwt) throws ExecutionException, InterruptedException {
        return consentService.getApiConsents(FintJwtEndUserPrincipal.from(jwt));
    }

//    @PostMapping("/consents")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<ApiConsent> addApiConsent(String processingId, @AuthenticationPrincipal Jwt jwt){
//        return consentService.addApiConsent(processingId, FintJwtEndUserPrincipal.from(jwt));
//    }




}
