package com.agenda.core.service;

import com.agenda.core.domain.Contato;
import com.agenda.core.domain.TipoContato;
import com.agenda.core.exception.RegraNegocioException; // Note o novo import
import com.agenda.core.ports.in.ContatoUseCase;
import com.agenda.core.ports.out.ContatoRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

public class ContatoUseCaseImpl implements ContatoUseCase {

    private final ContatoRepositoryPort repositoryPort;

    // Injeção de dependência pura via construtor
    public ContatoUseCaseImpl(ContatoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Contato incluir(Contato contato) {
        if (repositoryPort.existePorEmail(contato.getEmail())) {
            throw new RegraNegocioException("Já existe um contato com este e-mail!");
        }

        contato.setDataCad(LocalDateTime.now());
        // O log que existia foi removido para manter o core puro,
        // mas você pode usar bibliotecas de log como SLF4J aqui sem quebrar a arquitetura se desejar.

        return repositoryPort.salvar(contato);
    }

    @Override
    public List<Contato> listar() {
        return repositoryPort.buscarTodos();
    }

    @Override
    public Contato editar(Long id, Contato contatoAtualizado) {
        Contato contatoExistente = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RegraNegocioException("Contato não encontrado."));

        if (!contatoExistente.getEmail().equals(contatoAtualizado.getEmail())
                && repositoryPort.existePorEmail(contatoAtualizado.getEmail())) {
            throw new RegraNegocioException("Este e-mail já está em uso por outro contato.");
        }

        contatoExistente.setNome(contatoAtualizado.getNome());
        contatoExistente.setTel(contatoAtualizado.getTel());
        contatoExistente.setEmail(contatoAtualizado.getEmail());
        contatoExistente.setEndereco(contatoAtualizado.getEndereco());
        contatoExistente.setIdade(contatoAtualizado.getIdade());
        contatoExistente.setTipo(contatoAtualizado.getTipo());
        contatoExistente.setAtivo(contatoAtualizado.isAtivo());

        return repositoryPort.salvar(contatoExistente);
    }

    @Override
    public void excluir(Long id) {
        Contato contato = repositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RegraNegocioException("Contato não encontrado."));

        if (contato.getTipo() == TipoContato.FAMILIA) {
            throw new RegraNegocioException("Não é permitido excluir contatos do tipo FAMILIA.");
        }

        repositoryPort.excluir(contato);
    }

    @Override
    public List<Contato> pesquisar(String tipoBusca, String valor) {
        return repositoryPort.pesquisar(tipoBusca, valor);
    }
}