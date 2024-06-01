package br.com.loja.model.dao;



import br.com.loja.model.entities.Departamentos;
import br.com.loja.model.entities.Vendedores;

import java.util.List;

public interface VendedoresDao {
    void insert(Vendedores obj);
    void update(Vendedores obj);
    void deleteById(Integer id);
    Vendedores findById(Integer id);
    List<Vendedores> findAll();
    List<Vendedores> findByDepartamentos(Departamentos departamentos);

}
