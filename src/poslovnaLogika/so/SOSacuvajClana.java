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
public class SOSacuvajClana extends OpstaSO{
    private Clan clan;

    public SOSacuvajClana(Clan clan) {
        this.clan = clan;
    }
    
    @Override
    protected void proveriPreduslove() throws Exception,SQLObjekatPostojiException {
        SOVratiSveClanove so = new SOVratiSveClanove();
        so.opsteIzvrsenje();
        List<Clan> clanovi = so.getClanovi();
        if (clanovi.contains(clan)) {
            throw new SQLObjekatPostojiException("Clan sa datim imenom vec postoji u bazi!");
        }
    }

    @Override
    protected void izvrsi() throws Exception {
        dbbr.insertClan(clan);
    }
    
}
