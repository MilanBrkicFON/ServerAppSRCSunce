/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import request.RequestObject;
import response.ResponseObject;

/**
 *
 * @author Milan
 */
public class Komunikacija {

    private static Komunikacija instanca = null;

    private Komunikacija() throws IOException {
    }

    public static Komunikacija vratiInstancu() throws IOException {
        if (instanca == null) {
            instanca = new Komunikacija();
        }
        return instanca;
    }

    public void posaljiOdgovor(ResponseObject response, Socket socket) throws IOException {
        new ObjectOutputStream(socket.getOutputStream()).writeObject(response);
    }

    public RequestObject  procitajZahtev(Socket socket) throws IOException, ClassNotFoundException {
        return (RequestObject) new ObjectInputStream(socket.getInputStream()).readObject();

    }
}
