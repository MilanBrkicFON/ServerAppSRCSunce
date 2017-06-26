/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Trener;
import domen.Trening;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOVratiSveTrenereNaTreningu extends OpstaSO {

    private List<Trener> treneri;
    private final Trening trening;

    public SOVratiSveTrenereNaTreningu(Trening trening) {
        this.trening = trening;
    }

    @Override
    protected void proveriPreduslove() throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
//        treneri = dbbr.getAllTreneri(trening);
    }

    public List<Trener> getTreneri() {
        return treneri;
    }

}
