package net.chyson.main;

import net.chyson.server.JServer;

/**
 * michael.chyson
 * 5/30/2018
 */
public class Main {
    public static void main(String[] args) {
        new Thread(new JServer(12345)).start();
    }
}
