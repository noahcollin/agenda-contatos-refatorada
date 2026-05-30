package com.agenda.adapters.in.web.dto;

import com.agenda.core.domain.TipoContato;
import jakarta.validation.constraints.*;

public record ContatoRequestDTO(

        @NotBlank(message = "erro: nome obrigatorio")
        @Size(min = 3, message = "erro: nome muito curto")
        String nome,

        @NotBlank(message = "erro: telefone obrigatorio")
        String tel,

        @NotBlank(message = "erro: email obrigatorio")
        @Email(message = "erro: email invalido")
        String email,

        String endereco, // Endereço não tinha validação de obrigatoriedade no original

        @Min(value = 0, message = "erro: idade invalida")
        @Max(value = 150, message = "erro: idade invalida")
        int idade,

        @NotNull(message = "erro: tipo obrigatorio. Use: FAMILIA, AMIGO, TRABALHO ou OUTRO")
        TipoContato tipo,

        Boolean ativo
) {
}