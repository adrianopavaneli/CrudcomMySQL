package br.com.loja.model.dao;

import br.com.loja.db.DB;
import br.com.loja.model.dao.impl.AgendaDaoJDBC;
import br.com.loja.model.dao.impl.DepartamentosDaoJDBC;
import br.com.loja.model.dao.impl.VendedoresDaoJDBC;

public class DaoFactory {
    public static AgendaDao createAgendaDao(){
        return new AgendaDaoJDBC(DB.getConnection());

    }
    public static VendedoresDao createVendedoresDao() {
        return new VendedoresDaoJDBC(DB.getConnection());
    }

    public static DepartamentosDao createDepartamentosDao() {
        return new DepartamentosDaoJDBC(DB.getConnection());
    }
}

