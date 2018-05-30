package net.chyson.server;

import net.chyson.servlet.MichaelServlet;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.server.Server;

/**
 * michael.chyson
 * 5/30/2018
 */
public class JServer implements Runnable {
    private final Server server;
    private final ServletHandler handler;

    public JServer(int port) {
        this.server = new Server(port);
        this.handler = new ServletHandler();
    }

    @Override
    public void run() {
        System.out.println("Start Embedded Jetty!");
        server.setHandler(handler);
        handler.addServletWithMapping(MichaelServlet.class, "/*");
        try {
            server.start();
//            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
