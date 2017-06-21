/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Trener;
import domen.Trening;
import greske.SQLObjekatPostojiException;

/**
 *
 * @author Korisnik
 */
public class SOUbaciTreneraNaTrening extends OpstaSO{
    private final Trener trener;
    private final Trening trening;

    public SOUbaciTreneraNaTrening(Trener clan, Trening trening) {
        this.trener = clan;
        this.trening = trening;
    }
    
    
    @Override
    protected void proveriPreduslove() throws Exception,SQLObjekatPostojiException {
        //nema preduslove
    }

    @Override
    protected void izvrsi() throws Exception {
        dbbr.insertTrenerOnTraining(trener, trening);
    }
    
}
