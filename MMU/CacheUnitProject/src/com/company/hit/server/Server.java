package com.company.hit.server;

import com.company.hit.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * running the server
 * server : socket for getting from clients
 * anyClient : socket for communicate with a client
 * controller : report to cache unit about closing the server
 * shutdown, isSocketExist : flags to control the turn on of the server and the communication with the client
 */
public class Server
        extends java.lang.Object
        implements java.beans.PropertyChangeListener, java.lang.Runnable {

    private ServerSocket server;
    private Socket anyClient;
    private final CacheUnitController controller;
    private boolean shutdown;
    private boolean isSocketExist;

    /**
     * constructor
     *
     * @throws IOException if open socket fails
     */
    public Server() throws IOException {
        server = new ServerSocket(12345);
        controller = new CacheUnitController();
        shutdown = true;
        isSocketExist = true;
    }

    /**
     * start thread for server
     *
     * @param evt : for getting command from CLI
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue().equals("START")) {
            if (!shutdown) return;
            shutdown = false;
            isSocketExist = true;
            new Thread(this).start();

        } else if (evt.getNewValue().equals("STOP") && !shutdown) {
            isSocketExist = false;
            shutdown = true;
            controller.updated();
        }
    }

    /**
     * running server - get clients in an endless loop until the server shuts down
     */
    @Override
    public void run() {
        while (!shutdown) {
            try {
                anyClient = server.accept();

                if (isSocketExist) {
                    Thread thread = new Thread(new HandleRequest<String>(anyClient, controller));
                    thread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
