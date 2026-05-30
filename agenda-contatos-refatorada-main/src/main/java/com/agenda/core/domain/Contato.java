package com.agenda.core.domain;

import java.time.LocalDateTime;

// NENHUMA anotação do JPA ou do Spring aqui! Apenas Java puro.
public class Contato {

    private Long id;
    private String nome;
    private String tel;
    private String email;
    private String endereco;
    private int idade;
    private TipoContato tipo;
    private LocalDateTime dataCad;
    private boolean ativo;

    // Construtor vazio
    public Contato() {
    }

    // Construtor completo
    public Contato(Long id, String nome, String tel, String email, String endereco, int idade, TipoContato tipo, LocalDateTime dataCad, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.tel = tel;
        this.email = email;
        this.endereco = endereco;
        this.idade = idade;
        this.tipo = tipo;
        this.dataCad = dataCad;
        this.ativo = ativo;
    }

    // Pode gerar todos os Getters e Setters normalmente abaixo...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public TipoContato getTipo() { return tipo; }
    public void setTipo(TipoContato tipo) { this.tipo = tipo; }
    public LocalDateTime getDataCad() { return dataCad; }
    public void setDataCad(LocalDateTime dataCad) { this.dataCad = dataCad; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}