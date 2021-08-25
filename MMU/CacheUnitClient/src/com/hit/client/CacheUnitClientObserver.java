package com.hit.client;

import com.hit.view.CacheUnitView;

import java.lang.Object;
import java.beans.*;
import java.util.Scanner;

/**
 * receives a request from the client, sends it for execution and returns a response
 * client : transmit everything to execution
 */
public class CacheUnitClientObserver
        extends Object
        implements PropertyChangeListener {

    private CacheUnitClient client;

    /**
     * constructor
     */
    public CacheUnitClientObserver() {

        client = new CacheUnitClient();
    }

    /**
     * receives a request in the evt parameter, sends for handling and responds in the UI
     *
     * @param evt : comes from client
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        CacheUnitView view = (CacheUnitView) evt.getSource();
        if (evt.getNewValue().equals("statistic")) {
            try {
                String res = client.send("statistic");
                String resToClient;
                if (res.split(" ")[0].equals("\"error: ")) {
                    if (res.contains("\"")) resToClient = res.split("\"")[1] + ";\nfail";
                    else resToClient = res + ";\nfail";
                } else resToClient = res;
                view.updateUIData(resToClient);
            } catch (Exception e) {
                view.updateUIData("error: " + e.getMessage());
            }
            return;
        }
        Scanner text = (Scanner) evt.getNewValue();
        String stringData = "";
        while (text.hasNextLine()) {
            stringData = stringData + text.nextLine();
        }
        try {
            String res = client.send(stringData);
            String resToClient;
            if (res.equals("true")) resToClient = "success";
            else if (res.equals("false")) resToClient = "fail";
            else if (res.split(" ")[0].equals("\"error:")) {
                if (res.contains("\"")) resToClient = res.split("\"")[1] + ";\nfail";
                else resToClient = res + ";\nfail";
            } else resToClient = res + ";\nsuccess";
            view.updateUIData(resToClient);
        } catch (Exception e) {
            view.updateUIData("error: " + e.getMessage());
        }

    }
}