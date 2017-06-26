/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.TClan;
import domen.Trening;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Milan
 */
public class SOObrisiClanoveSaTreninga extends OpstaSO{
    private final List<Clan> clanovi;
    private final Trening trening;

    public SOObrisiClanoveSaTreninga(List<Clan> clanovi, Trening trening) {
        this.clanovi = clanovi;
        this.trening = trening;
    }
    
    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        for (Clan clan : clanovi) {
            dbbr.obrisi(new TClan(clan, trening));
        }
    }
    
}
