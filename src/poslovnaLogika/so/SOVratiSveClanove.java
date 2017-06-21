/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOVratiSveClanove extends OpstaSO{
    private List<Clan> clanovi;
    
    @Override
    protected void proveriPreduslove() throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        clanovi=dbbr.getAllClanovi();
    }
    
    public List<Clan> getClanovi(){
        return clanovi;
    }
    
}
