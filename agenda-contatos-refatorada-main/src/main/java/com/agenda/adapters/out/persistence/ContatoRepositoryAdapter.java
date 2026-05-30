package com.agenda.adapters.out.persistence;

import com.agenda.core.domain.Contato;
import com.agenda.core.ports.out.ContatoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContatoRepositoryAdapter implements ContatoRepositoryPort {

    private final SpringDataContatoRepository springRepository;

    public ContatoRepositoryAdapter(SpringDataContatoRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Contato salvar(Contato contato) {
        ContatoEntity entity = toEntity(contato);
        ContatoEntity salvo = springRepository.save(entity);
        return toDomain(salvo);
    }

    @Override
    public List<Contato> buscarTodos() {
        return springRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Contato> buscarPorId(Long id) {
        return springRepository.findById(id).map(this::toDomain);
    }

    @Override
    public void excluir(Contato contato) {
        springRepository.delete(toEntity(contato));
    }

    @Override
    public boolean existePorEmail(String email) {
        return springRepository.existsByEmail(email);
    }

    @Override
    public List<Contato> pesquisar(String tipoBusca, String valor) {
        // Usa a nova specification focada na Entity
        var spec = ContatoEntitySpecifications.porCampo(tipoBusca, valor);

        // Faz a busca no banco, converte de Entity para Domain, e retorna
        return springRepository.findAll(spec).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS DE CONVERSÃO (MAPPERS) ---
    private ContatoEntity toEntity(Contato dominio) {
        if (dominio == null) return null;
        return new ContatoEntity(
                dominio.getId(), dominio.getNome(), dominio.getTel(), dominio.getEmail(),
                dominio.getEndereco(), dominio.getIdade(), dominio.getTipo(),
                dominio.getDataCad(), dominio.isAtivo()
        );
    }

    private Contato toDomain(ContatoEntity entity) {
        if (entity == null) return null;
        return new Contato(
                entity.getId(), entity.getNome(), entity.getTel(), entity.getEmail(),
                entity.getEndereco(), entity.getIdade(), entity.getTipo(),
                entity.getDataCad(), entity.isAtivo()
        );
    }
}