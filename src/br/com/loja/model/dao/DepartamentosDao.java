package br.com.loja.model.dao;

import br.com.loja.model.entities.Departamentos;

import java.util.List;

public interface DepartamentosDao {
    void insert(Departamentos obj);
    void update(Departamentos obj);
    void deleteById(Integer id);
    Departamentos findById(Integer id);
    List<Departamentos> findAll();
}
