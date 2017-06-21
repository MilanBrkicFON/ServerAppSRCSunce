/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Trener;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOVratiSveTrenere extends OpstaSO{
    private List<Trener> treneri;
    
    @Override
    protected void proveriPreduslove() throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        treneri=dbbr.getAllTreneri();
    }
    
    public List<Trener> getTreneri(){
        return treneri;
    }
    
}
