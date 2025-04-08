package edu.ifmg.br.produto.services.exceptions;

public class DataBaseException  extends RuntimeException{

    public DataBaseException(){
        super();
    }

    public DataBaseException(String msg){
        super(msg);
    }
}
