package br.com.loja.model.dao.impl;

import br.com.loja.db.DB;
import br.com.loja.db.DbException;
import br.com.loja.db.DbIntegrityException;
import br.com.loja.model.dao.AgendaDao;
import br.com.loja.model.entities.Agenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDaoJDBC implements AgendaDao {
    private Connection conn;
    public AgendaDaoJDBC(Connection conn){
        this.conn = conn;
    }


    @Override
    public void insert(Agenda obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
               "INSERT INTO agenda " +
               "(nome, idade,dataCadastro) " +
               "VALUES " +
               "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

                st.setString(1, obj.getNome());
                st.setInt(2,obj.getIdade());
                java.util.Date dataUtil = new java.util.Date();
                dataUtil = obj.getDataCadastro();
                java.sql.Timestamp dataSql = new java.sql.Timestamp(dataUtil.getTime());
                st.setTimestamp(3, dataSql);

                int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
            }else{
                    throw new DbException("Erro Inexperado! Nenhuma linha Salva");
                }

        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Agenda obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
            "UPDATE agenda SET nome = ?, idade = ? WHERE id = ?");
            st.setString(1,obj.getNome());
            st.setInt(2,obj.getIdade());
            st.setInt(3,obj.getId());
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbException((e.getMessage()));
        }finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM agenda WHERE id = ?");
            st.setInt(1,id);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DbIntegrityException(e.getMessage());
        }finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public Agenda findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
              "SELECT * from agenda WHERE id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Agenda obj = new Agenda();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setIdade(rs.getInt("Idade"));
                return obj;
            }
            return null;

        }catch (SQLException e){
            throw  new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }
    }

    @Override
    public List<Agenda> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT * FROM agenda ORDER BY nome");
            rs = st.executeQuery();
            List<Agenda> list = new ArrayList<>();
            while(rs.next()){
                Agenda obj = new Agenda();
                obj.setId(rs.getInt("Id"));
                obj.setNome(rs.getString("Nome"));
                obj.setIdade(rs.getInt("Idade"));
                list.add(obj);
            }
            return list;
        }catch(SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closResultSet(rs);
        }

    }
}
