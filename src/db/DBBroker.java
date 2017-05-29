/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Clan;
import domen.Mesto;
import domen.Sport;
import domen.Trener;
import domen.Trening;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Milan
 */
public class DBBroker {

    private Connection connection;

    public DBBroker() {

    }

    public void uspostaviKonekcijuNaBazu() throws ClassNotFoundException, IOException, SQLException {
        Class.forName(Util.getInstance().getDriver());
        String url = Util.getInstance().getUrl();
        String user = Util.getInstance().getUser();
        String pass = Util.getInstance().getPassword();
        connection = DriverManager.getConnection(url, user, pass);
        connection.setAutoCommit(false);
        //System.out.println("Uspesno uspostavljena konekcija.");
    }

    public void raskiniKonekciju() throws SQLException {
        connection.close();
        //System.out.println("Raskinuta konekcija.");
    }

    public void potvrdiTransakciju() throws SQLException {
        connection.commit();
        //System.out.println("potvrdio transakciju");
    }

    public void ponistiTransakciju() throws SQLException {
        connection.rollback();
       //System.out.println("ponistio transakciju");
    }

    public List<Clan> getAllClanovi() throws SQLException {
        String upit = "SELECT clanId, ime, prezime, imeRoditelja, datumRodjenja, pol, godinaUpisa, m.ptt, m.naziv "
                + "FROM clan INNER JOIN mesto AS m ON clan.ptt = m.ptt";
        System.out.println(upit);
        Statement statement = connection.prepareStatement(upit);
        ResultSet rs = statement.executeQuery(upit);

        List<Clan> clanovi = new ArrayList<>();
        while (rs.next()) {
            int clanId = rs.getInt("clanId");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            String imeRod = rs.getString("imeRoditelja");
            LocalDate date = rs.getDate("datumRodjenja").toLocalDate();
            char pol = rs.getString("pol").charAt(0);
            int godinaUpisa = rs.getInt("godinaUpisa");

            Mesto m = new Mesto();
            m.setPtt(rs.getInt("ptt"));
            m.setNaziv(rs.getString("naziv"));

            Clan c = new Clan(clanId, ime, prezime, imeRod, pol, date, godinaUpisa, m);
            clanovi.add(c);
        }
        rs.close();
        statement.close();
        return clanovi;
    }

    public List<Trener> getAllTreneri() throws SQLException {
        String upit = "SELECT trenerID, ime, prezime, datumRodjenja, godineRada, kratakCV,s.sportID as sportid, s.naziv as naziv,s.maxBrClanova as maxBr "
                + "FROM trener "
                + "INNER JOIN sport AS s ON trener.sportID = s.sportID";
        System.out.println(upit);

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(upit);

        List<Trener> treneri = new ArrayList<>();
        while (rs.next()) {
            int trenerId = rs.getInt("trenerID");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            LocalDate datumRodj = rs.getDate("datumRodjenja").toLocalDate();
            int godineRada = rs.getInt("godineRada");
            String kratakCV = rs.getString("kratakCV");

            Sport s = new Sport();

            s.setSportID(rs.getInt("sportid"));
            s.setNaziv(rs.getString("naziv"));
            s.setMaxBrClanova(rs.getInt("maxBr"));

            Trener t = new Trener(trenerId, ime, prezime, datumRodj, godineRada, kratakCV, s);
            treneri.add(t);
        }
        rs.close();
        statement.close();

        return treneri;
    }

    public List<Trening> gatAllTrening() throws SQLException {
        String upit = "SELECT vremeOd, vremeDo, datum FROM trening";
        System.out.println(upit);
        PreparedStatement statement = connection.prepareStatement(upit);
        ResultSet rs = statement.executeQuery();

        List<Trening> treninzi = new ArrayList<>();
        while (rs.next()) {
            Trening t = new Trening();
            t.setVremeOd(rs.getTime("vremeOd").toLocalTime());
            t.setVremeDo(rs.getTime("vremeDo").toLocalTime());
            t.setDatum(rs.getDate("datum").toLocalDate());
            treninzi.add(t);
        }
        rs.close();
        statement.close();
        return treninzi;
    }

    public List<LocalDate> gatAllDatumi() throws SQLException {
        String upit = "SELECT DISTINCT datum FROM trening";
        System.out.println(upit);
        PreparedStatement statement = connection.prepareStatement(upit);
        ResultSet rs = statement.executeQuery();

        List<LocalDate> datumi = new ArrayList<>();
        while (rs.next()) {
            LocalDate datum = rs.getDate("datum").toLocalDate();
            datumi.add(datum);
        }
        rs.close();
        statement.close();
        return datumi;
    }

    /**
     * Populate trening object with setter methods for list of clanovi and list
     * of trening. You can get lists calling getter methods.
     *
     * @param trening
     * @throws SQLException
     */
    @Deprecated
    public void setAllClanAndTrenerZaTrening(Trening trening) throws SQLException {
        String upit = "SELECT c.clanid as cid, c.ime as cime, c.prezime as cprezime, t.trenerId as tid, t.ime as time, t.prezime as tprezime "
                + "FROM trening AS tr INNER JOIN tclan AS tc ON tr.vremeOd = tc.vremeOd AND tr.vremeDo = tc.vremeDo AND tr.datum = tc.datum "
                + "INNER JOIN clan AS c ON tc.clanid = c.clanid "
                + "INNER JOIN tt ON  tr.vremeOd = tt.vremeOD AND tr.vremeDo = tt.vremeDo AND tr.datum = tt.datum "
                + "INNER JOIN trener AS t ON tt.trenerId = t.trenerid "
                + "WHERE tr.datum = ? and tr.vremeOd = ? and tr.vremeDo = ?";
        System.out.println(upit);
        PreparedStatement statement = connection.prepareStatement(upit);
        statement.setDate(1, Date.valueOf(trening.getDatum()));
        statement.setTime(2, Time.valueOf(trening.getVremeOd()));
        statement.setTime(3, Time.valueOf(trening.getVremeDo()));

        ResultSet rs = statement.executeQuery();
        List<Object> obj = new ArrayList<>();
        List<Trener> treneri = new ArrayList<>();
        List<Clan> clanovi = new ArrayList<>();

        while (rs.next()) {
            Clan c = new Clan();
            c.setClanID(rs.getInt("cid"));
            c.setIme(rs.getString("cime"));
            c.setPrezime(rs.getString("cprezime"));
            if (!clanovi.contains(c)) {
                clanovi.add(c);
            }
            //System.out.println(c);
            Trener t = new Trener();
            t.setTrenerID(rs.getInt("tid"));
            t.setIme(rs.getString("time"));
            t.setPrezime(rs.getString("tprezime"));
            //System.out.println(t);
            if (!treneri.contains(t)) {
                treneri.add(t);
            }

        }
        trening.setClanovi(clanovi);
        trening.setTreneri(treneri);
    }

    public List<Clan> getAllClan(Trening trening) throws SQLException {
        String upit = "SELECT c.*, m.naziv AS naziv "
                + "FROM clan AS c LEFT JOIN mesto AS m ON c.ptt = m.ptt "
                + "JOIN tclan AS tc ON c.clanid = tc.cid "
                + "WHERE tc.vremeOd = ? AND tc.vremeDo = ? AND tc.datum = ?";

        PreparedStatement statement = connection.prepareStatement(upit);
        statement.setTime(1, Time.valueOf(trening.getVremeOd()));
        statement.setTime(2, Time.valueOf(trening.getVremeDo()));
        statement.setDate(3, Date.valueOf(trening.getDatum()));

        System.out.println(upit);
        ResultSet rs = statement.executeQuery();

        List<Clan> clanovi = new ArrayList<>();

        while (rs.next()) {
            Clan c = new Clan();
            c.setClanID(rs.getInt("clanId"));
            c.setIme(rs.getString("ime").trim());
            c.setPrezime(rs.getString("prezime").trim());
            c.setImeRoditelja(rs.getString("imeRoditelja"));
            c.setDatumRodjenja(rs.getDate("datumRodjenja").toLocalDate());
            c.setPol(rs.getString("pol").charAt(0));
            c.setGodinaUpisa(rs.getInt("godinaUpisa"));
            c.setMesto(new Mesto(rs.getInt("ptt"), rs.getString("naziv")));
            clanovi.add(c);
        }
        rs.close();
        statement.close();
        return clanovi;
    }

    public List<Trener> getAllTrener(Trening trening) throws SQLException {
        String upit = "SELECT t.* , s.naziv as naziv, s.maxBrClanova as maxBr "
                + "FROM trener AS t LEFT JOIN sport AS s ON t.sportID = s.sportID JOIN tt AS tt ON t.trenerID = tt.tID "
                + "WHERE tt.vremeOd = ? AND tt.vremeDo = ? AND tt.datum = ?";

        PreparedStatement statement = connection.prepareStatement(upit);
        statement.setTime(1, Time.valueOf(trening.getVremeOd()));
        statement.setTime(2, Time.valueOf(trening.getVremeDo()));
        statement.setDate(3, Date.valueOf(trening.getDatum()));

        System.out.println(upit);
        ResultSet rs = statement.executeQuery();

        List<Trener> treneri = new ArrayList<>();

        while (rs.next()) {
            Trener t = new Trener();
            t.setTrenerID(rs.getInt("trenerId"));
            t.setIme(rs.getString("ime").trim());
            t.setPrezime(rs.getString("prezime").trim());
            t.setDatumRodjenja(rs.getDate("datumRodjenja").toLocalDate());
            t.setGodineRada(rs.getInt("godineRada"));
            t.setKratakCV(rs.getString("kratakCV"));
            t.setSport(new Sport(rs.getInt("sportID"), rs.getString("naziv"), rs.getInt("maxBr")));
            treneri.add(t);
        }
        rs.close();
        statement.close();
        return treneri;
    }

    public List<Mesto> getAllMesto() throws SQLException {
        String upit = "SELECT ptt, naziv FROM mesto";
        System.out.println(upit);

        PreparedStatement statement = connection.prepareStatement(upit);
        ResultSet rs = statement.executeQuery();

        List<Mesto> mesta = new ArrayList<>();
        while (rs.next()) {
            Mesto m = new Mesto();
            m.setPtt(rs.getInt("ptt"));
            m.setNaziv(rs.getString("naziv"));
            mesta.add(m);
        }
        rs.close();
        statement.close();
        return mesta;
    }

    public List<Trening> getAllVreme(LocalDate datum) throws SQLException {
        String upit = "SELECT vremeOd, vremeDo "
                + "FROM trening WHERE datum = ?";
        System.out.println(upit);
        List<Trening> vremena;
        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setDate(1, Date.valueOf(datum));
            try (ResultSet rs = statement.executeQuery()) {
                vremena = new ArrayList<>();
                while (rs.next()) {
                    Trening t = new Trening();
                    t.setDatum(datum);
                    t.setVremeOd(rs.getTime("vremeOd").toLocalTime());
                    t.setVremeDo(rs.getTime("vremeDo").toLocalTime());
                    vremena.add(t);
                }
            }
        }
        return vremena;
    }

    public void insertClan(Clan clan) throws SQLException {

        String upit = "INSERT INTO clan(ime,prezime,imeroditelja,datumrodjenja,pol,godinaupisa,ptt) "
                + "VALUES (?,?,?,?,?,?,?)";

        System.out.println(upit);

        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setString(1, clan.getIme());
            statement.setString(2, clan.getPrezime());
            statement.setString(3, clan.getImeRoditelja());
            statement.setDate(4, Date.valueOf(clan.getDatumRodjenja()));
            statement.setString(5, String.valueOf(clan.getPol()));
            statement.setInt(6, clan.getGodinaUpisa());
            statement.setInt(7, clan.getMesto().getPtt());

            statement.executeUpdate();
        }
    }

    public void insertTrener(Trener trener) throws SQLException {

        String upit = "INSERT INTO trener(ime,prezime,datumrodjenja,godineRada,kratakcv,sportid) "
                + "VALUES (?,?,?,?,?,?)";

        System.out.println(upit);

        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setString(1, trener.getIme());
            statement.setString(2, trener.getPrezime());
            statement.setDate(3, Date.valueOf(trener.getDatumRodjenja()));
            statement.setInt(4, trener.getGodineRada());
            statement.setString(5, trener.getKratakCV());
            statement.setInt(6, trener.getSport().getSportID());
            statement.executeUpdate();
        }
    }
    public int vratiMaxIdTrener() throws SQLException {
        String upit = "SELECT MAX(trenerId) AS maxid FROM trener";
        System.out.println(upit);
        int maxId;
        try (PreparedStatement statement = connection.prepareStatement(upit);
                ResultSet rs = statement.executeQuery()) {
            maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt("maxid");
            }
        }
        return maxId;
    }
    
    public void obrisiClana(Clan clan) throws SQLException {
        String upit = "DELETE FROM clan WHERE clanID = ?";
        System.out.println(upit);
        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setInt(1, clan.getClanID());

            statement.executeUpdate();
        }
    }

    public int vratiMaxIdClan() throws SQLException {
        String upit = "SELECT MAX(clanid) AS maxid FROM clan";
        System.out.println(upit);
        int maxId;
        try (PreparedStatement statement = connection.prepareStatement(upit);
                ResultSet rs = statement.executeQuery()) {
            maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt("maxid");
            }
        }
        return maxId;
    }

    public void insertClanOnTraining(Clan c, Trening trening) throws SQLException {
        String upit = "INSERT INTO tclan(cid, vremeod, vremedo, datum) VALUES(?,?,?,?)";
        System.out.println(upit);
        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setInt(1, c.getClanID());
            statement.setTime(2, Time.valueOf(trening.getVremeOd()));
            statement.setTime(3, Time.valueOf(trening.getVremeDo()));
            statement.setDate(4, Date.valueOf(trening.getDatum()));

            statement.executeUpdate();
        }
    }

    public void updateClan(Clan clan) throws SQLException {
        String upit = "UPDATE clan SET ime = ?, prezime = ?, imeRoditelja = ?, datumRodjenja = ?, pol = ?, godinaUpisa = ?, ptt = ? where clanid = ?";
        System.out.println(upit);
        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setString(1, clan.getIme());
            statement.setString(2, clan.getPrezime());
            statement.setString(3, clan.getImeRoditelja());
            statement.setDate(4, Date.valueOf(clan.getDatumRodjenja()));
            statement.setString(5, String.valueOf(clan.getPol()));
            statement.setInt(6, clan.getGodinaUpisa());
            statement.setInt(7, clan.getMesto().getPtt());
            statement.setInt(8, clan.getClanID());

            statement.executeUpdate();
        }
    }

    public Collection<? extends Sport> getAllSport() throws SQLException {
        String upit = "SELECT sportId, naziv, maxBrClanova FROM sport";
        System.out.println(upit);

        List<Sport> sport;
        try (PreparedStatement statement = connection.prepareStatement(upit);
                ResultSet rs = statement.executeQuery()) {
            sport = new ArrayList<>();
            while (rs.next()) {
                Sport s = new Sport();
                s.setSportID(rs.getInt("sportId"));
                s.setNaziv(rs.getString("naziv"));
                s.setMaxBrClanova(rs.getInt("maxBrClanova"));
                sport.add(s);
            }
        }
        return sport;
    }

    public void updateTrener(Trener t) throws SQLException {
        String upit = "UPDATE trener SET ime = ?, prezime = ?, datumrodjenja = ?, godineRada = ?, kratakCV = ?, sportID = ? where trenerId = ?";
        System.out.println(upit);
        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setString(1, t.getIme());
            statement.setString(2, t.getPrezime());
            statement.setDate(3, Date.valueOf(t.getDatumRodjenja()));
            statement.setInt(4, t.getGodineRada());
            statement.setString(5, t.getKratakCV());
            statement.setInt(6, t.getSport().getSportID());
            statement.setInt(7, t.getTrenerID());

            statement.executeUpdate();
        }
    }

    public void obrisiTrenera(Trener trener) throws SQLException {
        String upit = "DELETE FROM trener WHERE trenerid = ?";
        System.out.println(upit);
        try (PreparedStatement statement = connection.prepareStatement(upit)) {
            statement.setInt(1, trener.getTrenerID());

            statement.executeUpdate();
        }
    }
}
