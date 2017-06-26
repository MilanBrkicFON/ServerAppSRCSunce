/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import greske.SQLObjekatPostojiException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOPromeniClanove extends OpstaSO {

    private List<Clan> clanovi;
    private List<Clan> promenjeniClanovi;

    public SOPromeniClanove(List<Clan> clanovi) {
        this.clanovi = clanovi;
        promenjeniClanovi = new ArrayList<>();
    }

    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        try {
            for (Clan clan : clanovi) {
                if (clan.isPromenjen()) {
                    dbbr.izmeniObjekat(clan);
                    promenjeniClanovi.add(clan);
                    clan.setPromenjen(false);
                }
            }
        } catch (Exception e) {
            throw new Exception("Desila se greška tokom izmene članova.");
        }
    }

    public List<Clan> getPromenjeniClanovi() {
        return promenjeniClanovi;
    }
}
