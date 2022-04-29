package no.fintlabs.controller;


import lombok.extern.slf4j.Slf4j;
import no.fint.model.resource.felles.PersonResource;
import no.fintlabs.service.PersonService;
import no.vigoiks.resourceserver.security.FintJwtEndUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public PersonResource getPerson(@AuthenticationPrincipal Jwt jwt) throws ExecutionException, InterruptedException {

        FintJwtEndUserPrincipal principal = FintJwtEndUserPrincipal.from(jwt);
        PersonResource person = personService.getPerson(principal);
        return person;


    }
}
