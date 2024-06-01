package br.com.loja.model.dao.impl;

import br.com.loja.db.DB;
import br.com.loja.db.DbException;
import br.com.loja.db.DbIntegrityException;
import br.com.loja.model.dao.DepartamentosDao;
import br.com.loja.model.entities.Departamentos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentosDaoJDBC implements DepartamentosDao {
    private Connection conn;

    public DepartamentosDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Departamentos findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM departamentos WHERE Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Departamentos obj = new Departamentos();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                return obj;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }
    }

    @Override
    public List<Departamentos> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM departamentos ORDER BY Nome");
            rs = st.executeQuery();

            List<Departamentos> list = new ArrayList<>();

            while (rs.next()) {
                Departamentos obj = new Departamentos();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                list.add(obj);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }
    }

    @Override
    public void insert(Departamentos obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO departamentos " +
                            "(Nome) " +
                            "VALUES " +
                            "(?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getNome());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Departamentos obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE departamentos " +
                            "SET Nome = ? " +
                            "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM departamentos WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }
}
