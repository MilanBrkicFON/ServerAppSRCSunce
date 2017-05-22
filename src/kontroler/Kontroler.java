/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import db.DBBroker;
import db.Util;
import domen.Clan;
import domen.Mesto;
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
import osluskivac.OsluskivacClanovi;

/**
 *
 * @author Milan
 */
public class Kontroler {

    private static Kontroler INSTANCE;
    private DBBroker dbbr;
    private List<OsluskivacClanovi> listeners;

    private Kontroler() {
        dbbr = new DBBroker();
        listeners = new ArrayList<>();
    }

    public static Kontroler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Kontroler();
        }
        return INSTANCE;
    }

    public void addListener(OsluskivacClanovi listener) {
        listeners.add(listener);
    }

    public void obavestiSveDodavanje(Clan clan) {
        for (OsluskivacClanovi listener : listeners) {
            listener.oDodajClana(clan);
        }
    }

    public void obavestiSveBrisanje(Clan clan) {
        for (OsluskivacClanovi listener : listeners) {
            listener.oObrisiClana(clan);
        }
    }

    public void uspostaviKonekcijuNaBazu() throws Exception {
        try {
            dbbr.uspostaviKonekcijuNaBazu();
            Util.getInstance().setStatus(true);
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

    public List<Clan> vratiSveClanove() throws Exception {

        uspostaviKonekcijuNaBazu();

        List<Clan> clanovi = new ArrayList<>();

        try {
            clanovi = dbbr.getAllClanovi();
            dbbr.potvrdiTransakciju();
        } catch (Exception e) {
            e.printStackTrace();
            dbbr.ponistiTransakciju();
        } finally {
            dbbr.raskiniKonekciju();
        }

        return clanovi;
    }

    public List<Trening> vratiSvaVremena(LocalDate datum) throws Exception {

        uspostaviKonekcijuNaBazu();

        List<Trening> treninzi = new ArrayList<>();
        try {
            treninzi = dbbr.getAllVreme(datum);
            dbbr.potvrdiTransakciju();
        } catch (Exception ex) {
            dbbr.ponistiTransakciju();
        } finally {
            dbbr.raskiniKonekciju();
        }
        return treninzi;
    }

    @Deprecated
    public List<Object> vratiSveNaTreningu(Trening trening) throws Exception {

        try {
            dbbr.uspostaviKonekcijuNaBazu();
        } catch (ClassNotFoundException ex) {
            throw new Exception("Konfiguracioni fajl nije pronadjen.");
        } catch (IOException ex) {
            throw new Exception("Greska prilikom otvaranja i/ili citanja konfiguracionog fajla.");
        } catch (SQLException ex) {
            throw new Exception("Greska prilikom povezivanja sa bazom ili url za bazu nije dobar.");
        }
        List<Object> ucesnici = new ArrayList<>();
        try {

            System.out.println();
            dbbr.potvrdiTransakciju();
        } catch (SQLException ex) {
            ex.printStackTrace();
            dbbr.ponistiTransakciju();
        } finally {
            dbbr.raskiniKonekciju();
        }
        return ucesnici;
    }

    public List<Clan> vratiSveClanove(Trening trening) throws Exception {

        uspostaviKonekcijuNaBazu();

        List<Clan> clanovi = new ArrayList<>();
        try {
            clanovi = dbbr.getAllClan(trening);

            dbbr.potvrdiTransakciju();
        } catch (SQLException ex) {
            ex.printStackTrace();
            dbbr.ponistiTransakciju();
        } finally {
            dbbr.raskiniKonekciju();
        }
        return clanovi;
    }

    public List<Trener> vratiSveTrenere(Trening trening) throws Exception {

        uspostaviKonekcijuNaBazu();

        List<Trener> treneri = new ArrayList<>();
        try {
            treneri = dbbr.getAllTrener(trening);

            dbbr.potvrdiTransakciju();
        } catch (SQLException ex) {
            ex.printStackTrace();
            dbbr.ponistiTransakciju();
        } finally {
            dbbr.raskiniKonekciju();
        }
        return treneri;
    }

    public void ubaciClana(Clan clan) throws Exception, SQLObjekatPostojiException {
        List<Clan> clanovi = vratiSveClanove();

        if (clanovi.contains(clan)) {
            throw new SQLObjekatPostojiException("Clan sa datim imenom vec postoji u bazi!");
        }

        uspostaviKonekcijuNaBazu();

        try {
            dbbr.insertClan(clan);
            dbbr.potvrdiTransakciju();
            obavestiSveDodavanje(clan);
        } catch (SQLException ex) {
            dbbr.ponistiTransakciju();
            throw new Exception("Greška prilikom ubacivanja člana.");
        } finally {
            dbbr.raskiniKonekciju();
        }

    }

    public void vratiMesta(List<Mesto> mesta) throws Exception {

        uspostaviKonekcijuNaBazu();

        try {
            mesta.addAll(dbbr.getAllMesto());
            dbbr.potvrdiTransakciju();
        } catch (Exception e) {
            dbbr.ponistiTransakciju();
            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + e.getMessage());
        } finally {
            dbbr.raskiniKonekciju();
        }
    }

    public List<LocalDate> vratiSveDatume() throws Exception {
        List<LocalDate> datumi = new ArrayList<>();

        uspostaviKonekcijuNaBazu();

        try {
            datumi = dbbr.gatAllDatumi();
            dbbr.potvrdiTransakciju();
        } catch (Exception e) {
            dbbr.ponistiTransakciju();
            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + e.getMessage());
        } finally {
            dbbr.raskiniKonekciju();
        }
        return datumi;
    }

    public void obrisi(Clan clan) throws Exception {
        uspostaviKonekcijuNaBazu();

        try {
            dbbr.obrisiClana(clan);
            dbbr.potvrdiTransakciju();
            obavestiSveBrisanje(clan);
        } catch (SQLException e) {
            dbbr.ponistiTransakciju();
            throw new Exception("Desila se greška prilikom brisanja člana.\n" + e.getMessage());
        } finally {
            dbbr.raskiniKonekciju();
        }
    }

    public static void main(String[] args) {
        try {
            Kontroler r = new Kontroler();
            List<Clan> ucesnici = new ArrayList<>();
            List<Trener> treneri = new ArrayList<>();

//            treneri = r.vratiSveTrenere(new Trening(LocalTime.of(13, 20, 0),
//                    LocalTime.of(14, 0, 0), LocalDate.of(2017, 4, 5)));
//
//            ucesnici = r.vratiSveClanove(new Trening(LocalTime.of(13, 20, 0),
//                    LocalTime.of(14, 0, 0), LocalDate.of(2017, 4, 5)));
            System.out.println(r.vratiSvaVremena(LocalDate.of(2017, 4, 5))
            );
            //System.out.println(ucesnici);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int vratiMaxId() throws Exception {
        uspostaviKonekcijuNaBazu();
        int i;
        try {
            i = dbbr.vratiMaxId();
            dbbr.potvrdiTransakciju();
        } catch (SQLException ex) {
            dbbr.ponistiTransakciju();
            i = 0;
            throw new Exception("Desila se greška prilikom preuzimanja podataka iz baze.\n" + ex.getMessage());
        } finally {
            dbbr.raskiniKonekciju();
        }
        return i;
    }

    public void ubaciNaTrening(Clan c, Trening trening) throws Exception {
        uspostaviKonekcijuNaBazu();
        try {
            dbbr.insertClanOnTraining(c, trening);
            dbbr.potvrdiTransakciju();
            obavestiSveDodavanje(c);
        } catch (Exception ex) {
            ex.printStackTrace();
            dbbr.ponistiTransakciju();
            throw new Exception("Desila se greška tokom ubacivanja člana na trening.");
        } finally {
            dbbr.raskiniKonekciju();
        }
    }
    
    public List<Clan> promeni(List<Clan> clanovi) throws Exception {
        uspostaviKonekcijuNaBazu();
        List<Clan> promenjeniClanovi = new ArrayList<>();
        try {
            for (Clan clan : clanovi) {
                if (clan.isPromenjen()) {
                    dbbr.updateClan(clan);
                    promenjeniClanovi.add(clan);
                    clan.setPromenjen(false);
                }
            }
            dbbr.potvrdiTransakciju();
        } catch (Exception ex) {
            ex.printStackTrace();
            dbbr.ponistiTransakciju();
            throw new Exception("Desila se greška tokom izmene članova.");
        } finally {
            dbbr.raskiniKonekciju();
        }
        return promenjeniClanovi;
    }
}
