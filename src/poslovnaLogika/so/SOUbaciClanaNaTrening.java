/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import domen.TClan;
import domen.Trening;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOUbaciClanaNaTrening extends OpstaSO{
    private final Clan clan;
    private final Trening trening;

    public SOUbaciClanaNaTrening(Clan clan, Trening trening) {
        this.clan = clan;
        this.trening = trening;
    }
    
    
    @Override
    protected void proveriPreduslove() throws Exception,SQLObjekatPostojiException {
        //nema preduslove
    }

    @Override
    protected void izvrsi() throws Exception {
//        dbbr.insertClanOnTraining(clan, trening);
        dbbr.sacuvajObjekat(new TClan(clan, trening));
    }
    
}
