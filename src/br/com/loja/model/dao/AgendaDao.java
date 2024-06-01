package br.com.loja.model.dao;

import br.com.loja.model.entities.Agenda;

import java.util.List;

public interface AgendaDao {
    void insert(Agenda obj);
    void update(Agenda obj);
    void deleteById(Integer id);
    Agenda findById(Integer id);
    List<Agenda> findAll();


}
