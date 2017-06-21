/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import db.DBBroker;
import greske.SQLObjekatPostojiException;
import java.sql.SQLException;

/**
 *
 * @author Milan
 */
public abstract class OpstaSO {

    protected DBBroker dbbr;

    public OpstaSO() {
        dbbr = new DBBroker();
    }
    
    public void uspostaviKonekciju() throws Exception{
        dbbr.uspostaviKonekcijuNaBazu();
    }
    protected abstract void proveriPreduslove() throws Exception,SQLObjekatPostojiException;

    protected abstract void izvrsi() throws Exception;

    private void potvrdiTransakciju() throws SQLException {
        dbbr.potvrdiTransakciju();
    }

    private void ponistiTransakciju() throws SQLException {
        dbbr.ponistiTransakciju();
    }

    private void raskiniKonekciju() throws SQLException {
        dbbr.raskiniKonekciju();
    }
    
    public void opsteIzvrsenje() throws Exception, SQLObjekatPostojiException{
        try {
            uspostaviKonekciju();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
        try{
            proveriPreduslove();
            izvrsi();
            potvrdiTransakciju();
        }catch(Exception e){
            e.printStackTrace();
            ponistiTransakciju();
            throw e;
        }finally{
            raskiniKonekciju();
        }
    }
}
