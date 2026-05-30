package com.agenda.core.ports.in;

import com.agenda.core.domain.Contato;
import java.util.List;

public interface ContatoUseCase {
    Contato incluir(Contato contato);
    List<Contato> listar();
    Contato editar(Long id, Contato contato);
    void excluir(Long id);
    List<Contato> pesquisar(String tipoBusca, String valor);
}