package com.hit.driver;

import java.lang.Object;

import com.hit.client.CacheUnitClientObserver;
import com.hit.view.CacheUnitView;

/**
 * main - running client program
 */
public class CacheUnitClientDriver extends Object {

    public CacheUnitClientDriver() {
    }

    public static void main(String[] args) {
        CacheUnitClientObserver cacheUnitClientObserver =
                new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();
    }
}
