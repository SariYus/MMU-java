package com.company.hit.services;

import com.company.hit.dm.DataModel;

/**
 * sends to cash unit service any request that came through the server
 *
 * @param <T> : type of data
 */
public class CacheUnitController<T> extends java.lang.Object {

    private CacheUnitService<T> CUService;

    public CacheUnitController() {
        CUService = new CacheUnitService();
    }

    public boolean delete(DataModel<T>[] dataModels) {
        return CUService.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return CUService.get(dataModels);
    }

    public boolean update(DataModel<T>[] dataModels) {
        return CUService.update(dataModels);
    }

    public String showStatistic() {
        return CUService.showStatistic();
    }

    public void updated() {
        CUService.updated();
    }
}
