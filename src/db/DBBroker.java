/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.OpstiDomenskiObjekat;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import util.Util;

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

    public void sacuvajObjekat(OpstiDomenskiObjekat odo) throws SQLException, Exception {

        Statement s = connection.createStatement();

        try {
            String sql = "INSERT INTO " + odo.vratiNazivTabele() + odo.vratiVrednostiZaInsert() + ")";
            System.out.println(sql);

            s.executeUpdate(sql);
            s.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public void izmeniObjekat(OpstiDomenskiObjekat odo) throws SQLException {
        try {
            Statement s = connection.createStatement();
            String upit = "UPDATE " + odo.vratiNazivTabele() + " SET " + odo.vratiVrednostiZaUpdate() + " " + odo.vratiUslovSaIdentifikatorom();
            System.out.println(upit);
            s.executeUpdate(upit);
            s.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    public List<? extends OpstiDomenskiObjekat> vratiListu(OpstiDomenskiObjekat odo) throws Exception {
        try {
            List<OpstiDomenskiObjekat> list;
            String sql = "SELECT * FROM " + odo.vratiNazivTabele() + " " + odo.vratiTabeluSaUslovomSpajanja();
            System.out.println(sql);
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(sql);
            list = odo.napuni(rs);
            rs.close();
            s.close();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void obrisi(OpstiDomenskiObjekat odo) throws SQLException {
        try {
            String sql = "DELETE FROM " + odo.vratiNazivTabele() + " " + odo.vratiUslovSaIdentifikatorom();
            System.out.println(sql);
            Statement s = connection.createStatement();
            s.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

//   
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

    public int vratiMaxIdTrening() throws SQLException {
        String upit = "SELECT MAX(treningId) AS maxid FROM trening";
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
}
