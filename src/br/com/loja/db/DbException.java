package br.com.loja.db;

public class DbException extends RuntimeException{
    public DbException(String msg){
        super(msg);
    }
}
