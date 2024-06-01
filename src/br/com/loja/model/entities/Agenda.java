package br.com.loja.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Agenda implements Serializable {
    private Integer id;
    private String nome;
    private Integer idade;
    private Date dataCadastro;

    public Agenda(){

    }

    public Agenda(Integer id, String nome, Integer idade, Date dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.dataCadastro = dataCadastro;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agenda agenda = (Agenda) o;
        return Objects.equals(nome, agenda.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nome);
    }

    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", dataCadastro=" + dataCadastro +
                '}';
    }
}
