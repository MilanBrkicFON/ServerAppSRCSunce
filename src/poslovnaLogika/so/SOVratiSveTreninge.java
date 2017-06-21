/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import domen.Trening;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOVratiSveTreninge extends OpstaSO{
    private List<Trening> treninzi;
    
    @Override
    protected void proveriPreduslove() throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        treninzi=dbbr.gatAllTreninzi();
    }
    
    public List<Trening> getTreninzi(){
        return treninzi;
    }
    
}
