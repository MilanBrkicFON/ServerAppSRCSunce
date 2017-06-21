/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnaLogika;

import db.DBBroker;
import db.Util;
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
import poslovnaLogika.so.SOObrisiTrenera;
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
import poslovnaLogika.so.SOVratiSvaMesta;
import poslovnaLogika.so.SOVratiSveClanove;
import poslovnaLogika.so.SOVratiSveClanoveNaTreningu;
import poslovnaLogika.so.SOVratiSveSportove;
import poslovnaLogika.so.SOVratiSveTrenere;
import poslovnaLogika.so.SOVratiSveTrenereNaTreningu;
import poslovnaLogika.so.SOVratiSveTreninge;

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

//        uspostaviKonekcijuNaBazu();
//
//        List<Clan> clanovi = new ArrayList<>();
//        try {
//            clanovi = dbbr.getAllClanovi(trening);
//
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOVratiSveClanoveNaTreningu so = new SOVratiSveClanoveNaTreningu(trening);
        so.opsteIzvrsenje();
        return so.getClanovi();
    }

    public List<Trener> vratiSveTrenere(Trening trening) throws Exception, SQLObjekatPostojiException {

//        uspostaviKonekcijuNaBazu();
//
//        List<Trener> treneri = new ArrayList<>();
//        try {
//            treneri = dbbr.getAllTrener(trening);
//
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOVratiSveTrenereNaTreningu so = new SOVratiSveTrenereNaTreningu(trening);
        so.opsteIzvrsenje();
        return so.getTreneri();
    }

    public void ubaciTrenera(Trener trener) throws Exception, SQLObjekatPostojiException {
//        List<Trener> clanovi = vratiSveTrenere();
//
//        if (clanovi.contains(trener)) {
//            throw new SQLObjekatPostojiException("Clan sa datim imenom vec postoji u bazi!");
//        }
//
//        uspostaviKonekcijuNaBazu();
//
//        try {
//            dbbr.insertTrener(trener);
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Greška prilikom ubacivanja člana.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOSacuvajTrenera so = new SOSacuvajTrenera(trener);
        so.opsteIzvrsenje();
    }

    public void ubaciClana(Clan clan) throws Exception, SQLObjekatPostojiException {
//        List<Clan> clanovi = vratiSveClanove();
//
//        if (clanovi.contains(clan)) {
//            throw new SQLObjekatPostojiException("Clan sa datim imenom vec postoji u bazi!");
//        }
//
//        uspostaviKonekcijuNaBazu();
//
//        try {
//            dbbr.insertClan(clan);
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            dbbr.ponistiTransakciju();
//            throw new Exception("Greška prilikom ubacivanja člana.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOSacuvajClana so = new SOSacuvajClana(clan);
        so.opsteIzvrsenje();
    }

    public void vratiMesta(List<Mesto> mesta) throws Exception, SQLObjekatPostojiException {

//        uspostaviKonekcijuNaBazu();
//
//        try {
//            mesta.addAll(dbbr.getAllMesto());
//            dbbr.potvrdiTransakciju();
//        } catch (Exception e) {
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + e.getMessage());
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOVratiSvaMesta so = new SOVratiSvaMesta();
        so.opsteIzvrsenje();
        mesta.addAll(so.getMesta());
    }

    public List<Trening> vratiSveTreninge() throws Exception, SQLObjekatPostojiException {
//        List<Trening> treninzi = new ArrayList<>();
//
//        uspostaviKonekcijuNaBazu();
//
//        try {
//            treninzi = dbbr.gatAllTreninzi();
//            dbbr.potvrdiTransakciju();
//        } catch (Exception e) {
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + e.getMessage());
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOVratiSveTreninge so = new SOVratiSveTreninge();
        so.opsteIzvrsenje();
        return so.getTreninzi();
    }

    public void obrisi(Clan clan) throws Exception, SQLObjekatPostojiException {
        SOObrisiClana so = new SOObrisiClana(clan);
        so.opsteIzvrsenje();
    }

    public int vratiMaxIdClan() throws Exception {
//        uspostaviKonekcijuNaBazu();
//        int i;
//        try {
//            i = dbbr.vratiMaxIdClan();
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            dbbr.ponistiTransakciju();
//            i = 0;
//            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + ex.getMessage());
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOVratiMaxIDTrener so = new SOVratiMaxIDTrener();
        so.opsteIzvrsenje();
        return so.getId();
    }

    public int vratiMaxIdTrener() throws Exception {
//        uspostaviKonekcijuNaBazu();
//        int i;
//        try {
//            i = dbbr.vratiMaxIdTrener();
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            dbbr.ponistiTransakciju();
//            i = 0;
//            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + ex.getMessage());
//        } finally {
//            dbbr.raskiniKonekciju();
//        }

        SOVratiMaxIDClan so = new SOVratiMaxIDClan();
        so.opsteIzvrsenje();
        return so.getId();
    }

    public void ubaciNaTrening(Clan c, Trening trening) throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        try {
//            dbbr.insertClanOnTraining(c, trening);
//            dbbr.potvrdiTransakciju();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška tokom ubacivanja člana na trening.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOUbaciClanaNaTrening so = new SOUbaciClanaNaTrening(c, trening);
        so.opsteIzvrsenje();
    }

    public void ubaciNaTrening(Trener t, Trening trening) throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        try {
//            dbbr.insertTrenerOnTraining(t, trening);
//            dbbr.potvrdiTransakciju();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška tokom ubacivanja člana na trening.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }

        SOUbaciTreneraNaTrening so = new SOUbaciTreneraNaTrening(t, trening);
        so.opsteIzvrsenje();
    }

    public void ubaciNaTrening(List<Trener> treneri, Trening trening) throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        try {
//            for (Trener t : treneri) {
//                dbbr.insertTrenerOnTraining(t, trening);
//            }
//            dbbr.potvrdiTransakciju();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška tokom ubacivanja člana na trening.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
         SOUbaciTrenereNaTrening so = new SOUbaciTrenereNaTrening(treneri, trening);
         so.opsteIzvrsenje();
    }

    public void sacuvajTrening(Trening trening) throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        try {
//            dbbr.addTrening(trening);
//
//            dbbr.potvrdiTransakciju();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška tokom ubacivanja člana na trening.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOSacuvajTrening so = new SOSacuvajTrening(trening);
        so.opsteIzvrsenje();
    }

    public List<Clan> promeni(List<Clan> clanovi) throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        List<Clan> promenjeniClanovi = new ArrayList<>();
//        try {
//            for (Clan clan : clanovi) {
//                if (clan.isPromenjen()) {
//                    dbbr.updateClan(clan);
//                    promenjeniClanovi.add(clan);
//                    clan.setPromenjen(false);
//                }
//            }
//            dbbr.potvrdiTransakciju();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška tokom izmene članova.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOPromeniClanove so = new SOPromeniClanove(clanovi);
        so.opsteIzvrsenje();
        return so.getPromenjeniClanovi();
    }

    public List<Sport> vratiSveSportove() throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        List<Sport> sporotovi = new ArrayList<>();
//        try {
//            sporotovi.addAll(dbbr.getAllSport());
//            dbbr.potvrdiTransakciju();
//        } catch (Exception e) {
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + e.getMessage());
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOVratiSveSportove so = new SOVratiSveSportove();
        so.opsteIzvrsenje();
        return so.getSportove();
    }

    public List<Trener> promeniTrenere(List<Trener> treneri) throws Exception, SQLObjekatPostojiException {
//        List<Trener> promenjenTrener = new ArrayList<>();
//        try {
//            for (Trener t : treneri) {
//                if (t.isPromenjen()) {
//                    dbbr.updateTrener(t);
//                    promenjenTrener.add(t);
//                    t.setPromenjen(false);
//                }
//            }
//            dbbr.potvrdiTransakciju();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška tokom izmene članova.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOPromeniTrenere so = new SOPromeniTrenere(treneri);
        so.opsteIzvrsenje();
        return so.getPromenjeniTreneri();
    }

    public List<Trener> vratiSveTrenere() throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//        List<Trener> treneri = new ArrayList<>();
//        try {
//            treneri = dbbr.getAllTreneri();
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            dbbr.ponistiTransakciju();
//            throw new Exception("Greška prilikom ubacivanja člana.");
//        } finally {
//            dbbr.raskiniKonekciju();
//        } 
        SOVratiSveTrenere so = new SOVratiSveTrenere();
        so.opsteIzvrsenje();
        return so.getTreneri();
    }

    public void obrisi(Trener trener) throws Exception, SQLObjekatPostojiException {
//        uspostaviKonekcijuNaBazu();
//
//        try {
//            dbbr.obrisiTrenera(trener);
//            dbbr.potvrdiTransakciju();
//        } catch (SQLException e) {
//            dbbr.ponistiTransakciju();
//            throw new Exception("Desila se greška prilikom brisanja člana.\n" + e.getMessage());
//        } finally {
//            dbbr.raskiniKonekciju();
//        }
        SOObrisiTrenera so = new SOObrisiTrenera(trener);
        so.opsteIzvrsenje();
    }
}
