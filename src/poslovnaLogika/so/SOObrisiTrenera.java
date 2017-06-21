/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Trener;
import greske.SQLObjekatPostojiException;

/**
 *
 * @author Korisnik
 */
public class SOObrisiTrenera extends OpstaSO{
    private Trener trener;

    public SOObrisiTrenera(Trener trener) {
        this.trener = trener;
    }
    
    @Override
    protected void proveriPreduslove() throws Exception,SQLObjekatPostojiException {
//  nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        dbbr.obrisiTrenera(trener);
    }
    
}
