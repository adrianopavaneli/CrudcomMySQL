package br.com.loja.model.dao.impl;

import br.com.loja.db.DB;
import br.com.loja.db.DbException;
import br.com.loja.model.dao.VendedoresDao;
import br.com.loja.model.entities.Departamentos;
import br.com.loja.model.entities.Vendedores;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendedoresDaoJDBC implements VendedoresDao {
    private Connection conn;

    public VendedoresDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vendedores obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO vendedores "
                            + "(Nome, Email, DataAniversario, SalarioBase, Departamentos_Id) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);



            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());


            LocalDate dataAniversario = obj.getDataAniversario();
            st.setDate(3, java.sql.Date.valueOf(dataAniversario));
            st.setDouble(4, obj.getSalarioBase());
            st.setInt(5, obj.getDepartamentos().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closResultSet(rs);
            }
            else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Vendedores obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE vendedores "
                            + "SET Nome = ?, Email = ?, DataAniversario = ?, SalarioBase = ?, Departamentos_Id = ? "
                            + "WHERE Id = ?");

            st.setString(1, obj.getNome());
            st.setString(2, obj.getEmail());
            LocalDate dataAniversario = obj.getDataAniversario();
            st.setDate(3, java.sql.Date.valueOf(dataAniversario));
            st.setDouble(4, obj.getSalarioBase());
            st.setInt(5, obj.getDepartamentos().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM vendedores WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Vendedores findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT vendedores.*,departamentos.Nome as DepNome "
                            + "FROM vendedores INNER JOIN departamentos "
                            + "ON vendedores.departamentos_Id = departamentos.Id "
                            + "WHERE vendedores.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Departamentos dep = instanciaDepartamentos(rs);
                Vendedores obj = instanciaVendedores(rs, dep);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }
    }

    private Vendedores instanciaVendedores(ResultSet rs, Departamentos dep) throws SQLException {
        Vendedores obj = new Vendedores();
        obj.setId(rs.getInt("Id"));
        obj.setNome(rs.getString("Nome"));
        obj.setEmail(rs.getString("Email"));
        obj.setSalarioBase(rs.getDouble("SalarioBase"));
        java.sql.Date sqlDate = rs.getDate("DataAniversario");
        if (sqlDate != null) {
            LocalDate localDate = sqlDate.toLocalDate();
            obj.setDataAniversario(localDate);
        } else {
            obj.setDataAniversario(null); // Ou qualquer valor padrão que você deseja usar para datas nulas
        }
        obj.setDepartamentos(dep);
        return obj;
    }

    private Departamentos instanciaDepartamentos(ResultSet rs) throws SQLException {
        Departamentos dep = new Departamentos();
        dep.setId(rs.getInt("Departamentos_Id"));
        dep.setNome(rs.getString("Nome"));
        return dep;
    }

    @Override
    public List<Vendedores> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT vendedores.*,departamentos.Nome as DepName "
                            + "FROM vendedores INNER JOIN departamentos "
                            + "ON vendedores.Departamentos_Id = departamentos.Id "
                            + "ORDER BY Nome");

            rs = st.executeQuery();

            List<Vendedores> list = new ArrayList<>();
            Map<Integer, Departamentos> map = new HashMap<>();

            while (rs.next()) {

                Departamentos dep = map.get(rs.getInt("Departamentos_Id"));

                if (dep == null) {
                    dep = instanciaDepartamentos(rs);
                    map.put(rs.getInt("Departamentos_Id"), dep);
                }

                Vendedores obj = instanciaVendedores(rs, dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }
    }

    @Override
    public List<Vendedores> findByDepartamentos(Departamentos departamentos) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT vendedores.*,departamentos.Nome as DepNome "
                            + "FROM vendedores INNER JOIN departamentos "
                            + "ON vendedores.Departamentos_Id = departamentos.Id "
                            + "WHERE Departamentos_Id = ? "
                            + "ORDER BY Nome");

            st.setInt(1, departamentos.getId());

            rs = st.executeQuery();

            List<Vendedores> list = new ArrayList<>();
            Map<Integer, Departamentos> map = new HashMap<>();

            while (rs.next()) {

                Departamentos dep = map.get(rs.getInt("Departamentos_Id"));

                if (dep == null) {
                    dep = instanciaDepartamentos(rs);
                    map.put(rs.getInt("Departamentos_Id"), dep);
                }

                Vendedores obj = instanciaVendedores(rs, dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }
    }
}
