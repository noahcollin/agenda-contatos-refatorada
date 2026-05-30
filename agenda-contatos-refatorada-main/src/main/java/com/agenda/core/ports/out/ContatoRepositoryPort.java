package com.agenda.core.ports.out;

import com.agenda.core.domain.Contato;
import java.util.List;
import java.util.Optional;

public interface ContatoRepositoryPort {
    Contato salvar(Contato contato);
    List<Contato> buscarTodos();
    Optional<Contato> buscarPorId(Long id);
    void excluir(Contato contato);
    boolean existePorEmail(String email);
    List<Contato> pesquisar(String tipoBusca, String valor);
}