package no.fintlabs.processing;

import no.fint.model.resource.personvern.samtykke.BehandlingResources;
import no.fintlabs.fint.FintClient;
import no.fintlabs.fint.FintEndpointConfiguration;
import no.vigoiks.resourceserver.security.FintJwtEndUserPrincipal;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProcessingService {
    private final FintClient fintclient;
    private final FintEndpointConfiguration fintEndpointConfiguration;

    public ProcessingService(FintClient fintclient, FintEndpointConfiguration fintEndpointConfiguration) {
        this.fintclient = fintclient;
        this.fintEndpointConfiguration = fintEndpointConfiguration;
    }

    public Mono<BehandlingResources> getProcessings(FintJwtEndUserPrincipal principal){
        return fintclient.getResource(fintEndpointConfiguration.getBaseUri() +
                fintEndpointConfiguration.getProcessingUri(), BehandlingResources.class);
    }

}
