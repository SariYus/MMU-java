package com.company.hit.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Scanner;

/**
 * command line to run server
 * in : commands file
 * out : printings file
 * observer : transmits commands to server
 */
public class CLI
        extends java.lang.Object
        implements java.lang.Runnable {

    private Scanner in;
    private DataOutputStream out;
    private PropertyChangeSupport observer;

    /**
     * constructor
     */
    public CLI(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new DataOutputStream(out);
        observer = new PropertyChangeSupport(this);
    }

    /**
     * add pcl parameter to property change listeners
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        observer.addPropertyChangeListener(pcl);
    }

    /**
     * remove pcl parameter from property change listeners
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        observer.removePropertyChangeListener(pcl);
    }

    /**
     * run CLI
     */
    @Override
    public void run() {
        while (true) {
            write("enter next command\n");
            String s = in.nextLine().toUpperCase(Locale.ROOT);
            if (s.equals("START")) {
                write("Starting server.......\n");
                observer.firePropertyChange("command", null, s);
            } else if (s.equals("STOP")) {
                write("Shutdown server\n");
                observer.firePropertyChange("command", null, s);
            } else if (!s.equals("EXIT")) {
                write("Not a valid command\n");
            }
        }
    }

    /**
     * write to out file
     *
     * @param string : string to write
     */
    public void write(String string) {
        try {
            out.writeUTF(string);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
