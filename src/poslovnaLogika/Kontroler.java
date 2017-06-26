/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika;

import db.DBBroker;
import domen.Clan;
import domen.Mesto;
import domen.Sport;
import domen.Trener;
import domen.Trening;
import greske.SQLObjekatPostojiException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import poslovnaLogika.so.SOObrisiClana;
import poslovnaLogika.so.SOObrisiClanoveSaTreninga;
import poslovnaLogika.so.SOObrisiTrenera;
import poslovnaLogika.so.SOObrisiTrenereSaTreninga;
import poslovnaLogika.so.SOPromeniClanove;
import poslovnaLogika.so.SOPromeniTrenere;
import poslovnaLogika.so.SOSacuvajClana;
import poslovnaLogika.so.SOSacuvajTrenera;
import poslovnaLogika.so.SOSacuvajTrening;
import poslovnaLogika.so.SOUbaciClanaNaTrening;
import poslovnaLogika.so.SOUbaciTreneraNaTrening;
import poslovnaLogika.so.SOUbaciTrenereNaTrening;
import poslovnaLogika.so.SOVratiMaxIDClan;
import poslovnaLogika.so.SOVratiMaxIDTrener;
import poslovnaLogika.so.SOVratiMaxIDTrening;
import poslovnaLogika.so.SOVratiSvaMesta;
import poslovnaLogika.so.SOVratiSveClanove;
import poslovnaLogika.so.SOVratiSveClanoveNaTreningu;
import poslovnaLogika.so.SOVratiSveSportove;
import poslovnaLogika.so.SOVratiSveTrenere;
import poslovnaLogika.so.SOVratiSveTrenereNaTreningu;
import poslovnaLogika.so.SOVratiSveTreninge;
import util.Util;

/**
 *
 * @author Milan
 */
public class Kontroler {

    private static Kontroler INSTANCE;
    private DBBroker dbbr;

    private Kontroler() {
        dbbr = new DBBroker();
    }

    public static Kontroler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Kontroler();
        }
        return INSTANCE;
    }

    public void uspostaviKonekcijuNaBazu() throws Exception {
        try {
            dbbr.uspostaviKonekcijuNaBazu();
            if (!Util.getInstance().isConnectedStatus()) {
                Util.getInstance().setStatus(true);
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            Util.getInstance().setStatus(false);
            throw new Exception("Konfiguracioni fajl nije pronadjen.");
        } catch (IOException ex) {
            ex.printStackTrace();
            Util.getInstance().setStatus(false);
            throw new Exception("Greska prilikom otvaranja i/ili citanja konfiguracionog fajla.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom povezivanja sa bazom ili url za bazu nije dobar.");
        }
    }

    public List<Clan> vratiSveClanove() throws Exception, SQLObjekatPostojiException {

        SOVratiSveClanove so = new SOVratiSveClanove();
        so.opsteIzvrsenje();

        return so.getClanovi();
    }

    

    public List<Clan> vratiSveClanove(Trening trening) throws Exception, SQLObjekatPostojiException {

        SOVratiSveClanoveNaTreningu so = new SOVratiSveClanoveNaTreningu(trening);
        so.opsteIzvrsenje();
        return so.getClanovi();
    }

    public List<Trener> vratiSveTrenere(Trening trening) throws Exception, SQLObjekatPostojiException {

        SOVratiSveTrenereNaTreningu so = new SOVratiSveTrenereNaTreningu(trening);
        so.opsteIzvrsenje();
        return so.getTreneri();
    }

    public void ubaciTrenera(Trener trener) throws Exception, SQLObjekatPostojiException {
        SOSacuvajTrenera so = new SOSacuvajTrenera(trener);
        so.opsteIzvrsenje();
    }

    public void ubaciClana(Clan clan) throws Exception, SQLObjekatPostojiException {
        SOSacuvajClana so = new SOSacuvajClana(clan);
        so.opsteIzvrsenje();
    }

    public void vratiMesta(List<Mesto> mesta) throws Exception, SQLObjekatPostojiException {

        SOVratiSvaMesta so = new SOVratiSvaMesta();
        so.opsteIzvrsenje();
        mesta.addAll(so.getMesta());
    }

    public List<Trening> vratiSveTreninge() throws Exception, SQLObjekatPostojiException {
        SOVratiSveTreninge so = new SOVratiSveTreninge();
        so.opsteIzvrsenje();
        return so.getTreninzi();
    }

    public void obrisi(Clan clan) throws Exception, SQLObjekatPostojiException {
        SOObrisiClana so = new SOObrisiClana(clan);
        so.opsteIzvrsenje();
    }

    public int vratiMaxIdClan() throws Exception {
        SOVratiMaxIDClan so = new SOVratiMaxIDClan();
        so.opsteIzvrsenje();
        return so.getId();
    }

    public int vratiMaxIdTrener() throws Exception {

        SOVratiMaxIDTrener so = new SOVratiMaxIDTrener();
        so.opsteIzvrsenje();
        return so.getId();
    }
    public int vratiMaxIdTrening() throws Exception {

        SOVratiMaxIDTrening so = new SOVratiMaxIDTrening();
        so.opsteIzvrsenje();
        return so.getId();
    }

    public void ubaciNaTrening(Clan c, Trening trening) throws Exception, SQLObjekatPostojiException {
        SOUbaciClanaNaTrening so = new SOUbaciClanaNaTrening(c, trening);
        so.opsteIzvrsenje();
    }

    public void ubaciNaTrening(Trener t, Trening trening) throws Exception, SQLObjekatPostojiException {
        SOUbaciTreneraNaTrening so = new SOUbaciTreneraNaTrening(t, trening);
        so.opsteIzvrsenje();
    }

    public void ubaciNaTrening(List<Trener> treneri, Trening trening) throws Exception, SQLObjekatPostojiException {
         SOUbaciTrenereNaTrening so = new SOUbaciTrenereNaTrening(treneri, trening);
         so.opsteIzvrsenje();
    }

    public void sacuvajTrening(Trening trening) throws Exception, SQLObjekatPostojiException {
        SOSacuvajTrening so = new SOSacuvajTrening(trening);
        so.opsteIzvrsenje();
    }

    public List<Clan> promeni(List<Clan> clanovi) throws Exception, SQLObjekatPostojiException {
        SOPromeniClanove so = new SOPromeniClanove(clanovi);
        so.opsteIzvrsenje();
        return so.getPromenjeniClanovi();
    }

    public List<Sport> vratiSveSportove() throws Exception, SQLObjekatPostojiException {
        SOVratiSveSportove so = new SOVratiSveSportove();
        so.opsteIzvrsenje();
        return so.getSportove();
    }

    public List<Trener> promeniTrenere(List<Trener> treneri) throws Exception, SQLObjekatPostojiException {
        SOPromeniTrenere so = new SOPromeniTrenere(treneri);
        so.opsteIzvrsenje();
        return so.getPromenjeniTreneri();
    }

    public List<Trener> vratiSveTrenere() throws Exception, SQLObjekatPostojiException {
        SOVratiSveTrenere so = new SOVratiSveTrenere();
        so.opsteIzvrsenje();
        return so.getTreneri();
    }

    public void obrisi(Trener trener) throws Exception, SQLObjekatPostojiException {
        SOObrisiTrenera so = new SOObrisiTrenera(trener);
        so.opsteIzvrsenje();
    }

    public void obrisiClanove(List<Clan> clanovi, Trening trening) throws Exception {
        SOObrisiClanoveSaTreninga so = new SOObrisiClanoveSaTreninga(clanovi,trening);
        so.opsteIzvrsenje();
    }
    
    public void obrisiTrenere(List<Trener> treneri, Trening trening) throws Exception {
        SOObrisiTrenereSaTreninga so = new SOObrisiTrenereSaTreninga(treneri,trening);
        so.opsteIzvrsenje();
    }
}
