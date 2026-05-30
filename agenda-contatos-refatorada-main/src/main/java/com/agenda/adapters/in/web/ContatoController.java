package com.agenda.adapters.in.web;

import com.agenda.adapters.in.web.dto.ContatoRequestDTO;
import com.agenda.adapters.in.web.dto.ContatoResponseDTO;
import com.agenda.core.domain.Contato;
import com.agenda.core.ports.in.ContatoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    // Veja que injetamos a INTERFACE (Porta), e não a implementação!
    private final ContatoUseCase useCase;

    public ContatoController(ContatoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/incluir")
    public ResponseEntity<ContatoResponseDTO> incluir(@RequestBody @Valid ContatoRequestDTO dto) {
        // 1. Converte o DTO da Web para o objeto de Domínio do Core
        Contato contatoDomain = toDomain(dto);

        // 2. Chama a regra de negócio do Core
        Contato salvo = useCase.incluir(contatoDomain);

        // 3. Converte o resultado do Core de volta para o DTO da Web
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(salvo));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ContatoResponseDTO>> listar() {
        List<ContatoResponseDTO> lista = useCase.listar().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ContatoResponseDTO> editar(@PathVariable Long id, @RequestBody @Valid ContatoRequestDTO dto) {
        Contato contatoDomain = toDomain(dto);
        Contato contatoAtualizado = useCase.editar(id, contatoDomain);
        return ResponseEntity.ok(toDto(contatoAtualizado));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        useCase.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<ContatoResponseDTO>> pesquisar(
            @RequestParam String tipoBusca,
            @RequestParam String valor) {
        List<ContatoResponseDTO> lista = useCase.pesquisar(tipoBusca, valor).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // --- MÉTODOS DE CONVERSÃO (MAPPERS) ---

    private Contato toDomain(ContatoRequestDTO dto) {
        Contato contato = new Contato();
        contato.setNome(dto.nome());
        contato.setTel(dto.tel());
        contato.setEmail(dto.email());
        contato.setEndereco(dto.endereco());
        contato.setIdade(dto.idade());
        contato.setTipo(dto.tipo());
        contato.setAtivo(dto.ativo() != null ? dto.ativo() : true);
        return contato;
    }

    private ContatoResponseDTO toDto(Contato contato) {
        return new ContatoResponseDTO(
                contato.getId(), contato.getNome(), contato.getTel(), contato.getEmail(),
                contato.getEndereco(), contato.getIdade(), contato.getTipo(),
                contato.getDataCad(), contato.isAtivo()
        );
    }
}