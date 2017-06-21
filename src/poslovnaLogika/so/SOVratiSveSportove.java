/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Mesto;
import domen.Sport;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOVratiSveSportove extends OpstaSO{
    private List<Sport> sportovi;
    
    @Override
    protected void proveriPreduslove() throws Exception {
        //nema preduslova
    }

    @Override
    protected void izvrsi() throws Exception {
        sportovi=dbbr.getAllSport();
    }
    
    public List<Sport> getSportove(){
        return sportovi;
    }
    
}
