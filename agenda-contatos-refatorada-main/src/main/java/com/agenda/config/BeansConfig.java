package com.agenda.config;

import com.agenda.core.ports.in.ContatoUseCase;
import com.agenda.core.ports.out.ContatoRepositoryPort;
import com.agenda.core.service.ContatoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public ContatoUseCase contatoUseCase(ContatoRepositoryPort repositoryPort) {
        // Aqui nós ensinamos o Spring a instanciar o nosso UseCase puro!
        return new ContatoUseCaseImpl(repositoryPort);
    }
}