/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika.so;

import domen.Clan;
import domen.Mesto;
import domen.Trening;
import greske.SQLObjekatPostojiException;
import java.util.List;

/**
 *
 * @author Korisnik
 */
public class SOSacuvajTrening extends OpstaSO {

    private Trening trening;

    public SOSacuvajTrening(Trening trening) {
        this.trening = trening;
    }

    @Override
    protected void proveriPreduslove() throws Exception, SQLObjekatPostojiException {
        SOVratiSveTreninge so = new SOVratiSveTreninge();
        so.opsteIzvrsenje();
        List<Trening> trenings = so.getTreninzi();
        for (Trening t : trenings) {
            if (t.getDatum().equals(trening.getDatum())) {
                if (trening.getVremeOd().isAfter(t.getVremeOd())
                        || trening.getVremeDo().isBefore(t.getVremeDo())
                        || (trening.getVremeOd().isBefore(t.getVremeDo()) && trening.getVremeOd().isAfter(t.getVremeOd()))
                        || (trening.getVremeDo().isAfter(t.getVremeOd()) && trening.getVremeDo().isBefore(t.getVremeDo())))  {

                    throw new SQLObjekatPostojiException("Vreme treninga se poklapa sa drugim treningom. Trening koji postoji: " + t.toString());
                }
            }
        }
    }

    @Override
    protected void izvrsi() throws Exception {
        dbbr.addTrening(trening);
    }

}
