package com.company.hit.server;

import com.company.hit.util.CLI;

import java.io.IOException;

/**
 * main - running server program
 */
public class CacheUnitServerDriver extends java.lang.Object {

    public CacheUnitServerDriver() {
    }

    public static void main(String[] args) throws IOException {
        CLI cli = new CLI(System.in, System.out);
        Server server = new Server();
        cli.addPropertyChangeListener(server);
        new Thread(cli).start();
    }
}
