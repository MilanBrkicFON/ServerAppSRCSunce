/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import domen.Trener;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOSacuvajTrenera extends OpstaSO{
    private final Trener trener;

    public SOSacuvajTrenera(Trener t) {
        this.trener = t;
    }
    
    @Override
    protected void proveriPreduslove() throws Exception,SQLObjekatPostojiException {
        SOVratiSveTrenere so = new SOVratiSveTrenere();
        so.opsteIzvrsenje();
        List<Trener> treneri = so.getTreneri();
        if (treneri.contains(trener)) {
            throw new SQLObjekatPostojiException("Trener sa datim imenom vec postoji u bazi!");
        }
    }

    @Override
    protected void izvrsi() throws Exception {
        dbbr.sacuvajObjekat(trener);
    }
    
}
