/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import server.Server;

/**
 *
 * @author Milan
 */
public class StartServer {
    public static void main(String[] args) {
        Server server = new Server(12321);
        new Thread(server).start();
    }
}
