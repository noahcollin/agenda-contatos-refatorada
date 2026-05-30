package com.agenda.adapters.in.web.dto;

import com.agenda.core.domain.TipoContato;
import java.time.LocalDateTime;

public record ContatoResponseDTO(
        Long id,
        String nome,
        String tel,
        String email,
        String endereco,
        int idade,
        TipoContato tipo,
        LocalDateTime dataCad,
        boolean ativo
) {
}