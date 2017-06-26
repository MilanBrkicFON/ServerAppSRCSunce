/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOObrisiClana extends OpstaSO{
    private Clan clan;

    public SOObrisiClana(Clan clan) {
        this.clan = clan;
    }
    
    @Override
    protected void proveriPreduslove() throws Exception,SQLObjekatPostojiException {
//  nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        dbbr.obrisi(clan);
    }
    
}
