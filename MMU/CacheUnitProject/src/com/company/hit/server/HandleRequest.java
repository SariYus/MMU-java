package com.company.hit.server;

import com.company.hit.dm.DataModel;
import com.company.hit.services.CacheUnitController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

/**
 * disassemble the request that comes from the server and transfer it to cache unit controller for treatment
 * reader : read request
 * writer : write response
 *
 * @param <T> : type of data
 */
public class HandleRequest<T>
        extends java.lang.Object
        implements java.lang.Runnable {

    private Socket s;
    private CacheUnitController<T> controller;
    private Request<DataModel<T>[]> request;
    private DataOutputStream writer;
    private InputStream reader;

    /**
     * constructor
     *
     * @param s          : socket to get input request
     * @param controller : transmit to cache unit the actions
     * @throws IOException if input / output stream fail
     */
    public HandleRequest(Socket s, CacheUnitController<T> controller) throws IOException {
        this.s = s;
        this.controller = controller;
        writer = new DataOutputStream(s.getOutputStream());
        reader = s.getInputStream();
    }

    /**
     * running handle request
     */
    @Override
    public void run() {

        DataInputStream in = new DataInputStream(new BufferedInputStream(reader));
        StringBuilder builder = new StringBuilder();
        String content = "";
        try {
            do {
                content = in.readUTF();
                builder.append(content);
            } while (in.available() != 0);
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }

        content = builder.toString();
        if (content.equals("statistic")) {
            try {
                writer.writeUTF(controller.showStatistic());
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Type ref = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        request = new Gson().fromJson(content, ref);
        String action = request.getHeaders().get("action");
        try {
            switch (action) {
                case "UPDATE":
                    writer.writeUTF(String.valueOf(controller.update(request.getBody())));
                    break;
                case "DELETE":
                    writer.writeUTF(String.valueOf(controller.delete(request.getBody())));
                    break;
                case "GET":
                    writer.writeUTF(new Gson().toJson(controller.get(request.getBody())));
                    break;
                default:
            }
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
