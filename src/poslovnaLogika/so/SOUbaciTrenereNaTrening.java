/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Trener;
import domen.Trening;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOUbaciTrenereNaTrening extends OpstaSO {

    private final List<Trener> treneri;
    private final Trening trening;

    public SOUbaciTrenereNaTrening(List<Trener> treneri, Trening trening) {
        this.treneri = treneri;
        this.trening = trening;
    }

    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        //nema preduslove
    }

    @Override
    protected void izvrsi() throws Exception {
        for (Trener t : treneri) {
            dbbr.insertTrenerOnTraining(t, trening);
        }
    }

}
