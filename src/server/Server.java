/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.KlijentNit;

/**
 *
 * @author Milan
 */
public class Server implements Runnable {

    protected int port = 12321;
    protected ServerSocket serverSocket = null;
    protected boolean zaustavljen = false;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server je podignut i osluskuje na portu 12321.\n\n");
            int i = 0;
            while (!zaustavljen) {
                i++;
                try {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new KlijentNit(clientSocket)).start();
                    System.out.println("Pokrenut start");
                    Thread.sleep(1000);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    if (jeZaustavljen()) {
                        System.out.println("Server je prestao sa radom...Proveri");
                        return;
                    }
                    throw new RuntimeException("Greska... Klijent se ne  moze povezati na server");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            System.out.println("Server ne radi!");
        } catch (IOException ex) {
            throw new RuntimeException("Server se ne moze podignuti na tom portu - Proveri gresku", ex);
        }

    }

    private synchronized boolean jeZaustavljen() {
        return this.zaustavljen;
    }

    public synchronized void zaustaviServer() {
        this.zaustavljen = true;
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            throw new RuntimeException("Server se ne moze zaustaviti - Please check error", ex);
        }
    }
}
