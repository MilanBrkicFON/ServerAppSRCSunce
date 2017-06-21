/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Trener;
import greske.SQLObjekatPostojiException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOPromeniTrenere extends OpstaSO {

    private final List<Trener> treneri;
    private List<Trener> promenjeniTreneri;

    public SOPromeniTrenere(List<Trener> treneri) {
        this.treneri = treneri;
        promenjeniTreneri = new ArrayList<>();
    }

    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        try {
            for (Trener t : treneri) {
                if (t.isPromenjen()) {
                    dbbr.updateTrener(t);
                    promenjeniTreneri.add(t);
                    t.setPromenjen(false);
                }
            }
        } catch (Exception e) {
            throw new Exception("Desila se greška tokom izmene trenera.");
        }
    }

    public List<Trener> getPromenjeniTreneri() {
        return promenjeniTreneri;
    }
}
