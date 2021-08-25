package com.hit.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * communicate with server
 */
public class CacheUnitClient extends Object {

    /**
     * default constructor
     */
    public CacheUnitClient() {

    }

    /**
     * send request to server through a socket
     *
     * @param request : request to send
     * @return response from server
     */
    public String send(String request) {

        String content = "";

        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            writer.writeUTF(request);
            writer.flush();

            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();

            do {
                content = in.readUTF();
                sb.append(content);
            } while (in.available() != 0);
            content = sb.toString();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}


