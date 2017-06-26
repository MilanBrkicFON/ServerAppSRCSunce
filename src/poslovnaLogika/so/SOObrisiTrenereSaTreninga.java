/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.TT;
import domen.Trener;
import domen.Trening;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Milan
 */
public class SOObrisiTrenereSaTreninga extends OpstaSO{
    private final List<Trener> treneri;
    private final Trening trening;

    public SOObrisiTrenereSaTreninga(List<Trener> tr, Trening trening) {
        this.treneri = tr;
        this.trening = trening;
    }
    
    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        for (Trener t : treneri) {
            dbbr.obrisi(new TT(t,trening));
        }
    }
    
}
