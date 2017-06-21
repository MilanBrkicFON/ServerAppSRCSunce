/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import greske.SQLObjekatPostojiException;

/**
 *
 * @author Milan
 */
public class SOVratiMaxIDTrener extends OpstaSO {

    private int id;

    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        //nema preduslove
    }

    @Override
    protected void izvrsi() throws Exception {
        id = dbbr.vratiMaxIdTrener();
    }

    public int getId() {
        return id;
    }
}