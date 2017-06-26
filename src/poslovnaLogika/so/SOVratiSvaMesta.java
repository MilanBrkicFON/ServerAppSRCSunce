/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Mesto;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOVratiSvaMesta extends OpstaSO{
    private List<Mesto> mesta;
    
    @Override
    protected void proveriPreduslove() throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        mesta=(List<Mesto>) dbbr.vratiListu(new Mesto());
    }
    
    public List<Mesto> getMesta(){
        return mesta;
    }
    
}
