package com.company.hit.services;

import com.company.MFUAlgoCacheImpl;
import com.company.hit.dao.DaoFileImpl;
import com.company.hit.dao.IDao;
import com.company.hit.dm.DataModel;
import com.company.hit.memory.CacheUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * runs memory requests
 * cacheUnit : executes commands on cache memory
 * idao : execute commands on disk memory
 * algo : algorithm to implement MMU
 * requestsNumber, dataModelsNumber, swapsNumber : counters for statistics
 *
 * @param <T> : type of data
 */
public class CacheUnitService<T> extends java.lang.Object {

    private CacheUnit cacheUnit;
    private final IDao<Long, DataModel<T>> idao;
    private MFUAlgoCacheImpl algo;
    private static int requestsNumber, dataModelsNumber, swapsNumber;

    /**
     * constructor
     */
    public CacheUnitService() {
        algo = new MFUAlgoCacheImpl<>(300);
        cacheUnit = new CacheUnit<>(algo);
        idao = new DaoFileImpl("src/resources/dataSource.json");
    }

    /**
     * delete data models from memory
     *
     * @param dataModels : array of data models to delete
     * @return success
     */
    public boolean delete(DataModel<T>[] dataModels) {
        requestsNumber++;
        dataModelsNumber += dataModels.length;
        try {
            Long[] ids = new Long[dataModels.length];
            for (int i = 0; i < dataModels.length; i++) {
                idao.delete(dataModels[i]);
                ids[i] = dataModels[i].getDataModelId();
            }
            cacheUnit.removeDataModels(ids);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * search data models in memory
     *
     * @param dataModels : array of data models to search
     * @return array with the found data or null values for data not found
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        requestsNumber++;
        dataModelsNumber += dataModels.length;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            ids[i] = dataModels[i].getDataModelId();
        }

        DataModel[] DMInCache = cacheUnit.getDataModels(ids);
        List<DataModel<T>> DMInDb = new ArrayList<DataModel<T>>();
        for (int i = 0; i < DMInCache.length; i++) {
            if (null == DMInCache[i]) {
                DataModel<T> notInCache = idao.find(ids[i]);
                if (notInCache != null) {
                    DMInDb.add(notInCache);
                    DMInCache[i] = notInCache;
                }
            }
        }
        DataModel<T>[] deleted = cacheUnit.putDataModels(DMInDb.toArray(new DataModel[DMInDb.size()]));
        for (DataModel<T> deletedObj : deleted) {
            idao.save(deletedObj);
        }
        return DMInCache;
    }

    /**
     * update data in memory
     *
     * @param dataModels : data models to update
     * @return success
     */
    public boolean update(DataModel<T>[] dataModels) {
        requestsNumber++;
        dataModelsNumber += dataModels.length;
        try {
            DataModel<T>[] deletedFromCache = cacheUnit.putDataModels(dataModels);
            for (DataModel<T> deletedObj : deletedFromCache) {
                if (null != deletedObj) {
                    idao.save(deletedObj);
                    swapsNumber++;
                }
            }
            Map<Long, DataModel<T>> inRam = algo.getRam();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * show statistic saved about requests
     *
     * @return string of details about requests
     */
    public String showStatistic() {
        return "Capacity: " + String.valueOf(algo.getCapacity()) +
                "\nAlgorithm: MFU" +
                "\nTotal number of request: " + requestsNumber +
                "\nTotal number of DataModels (GET/DELETE/UPDATE requests): " + dataModelsNumber +
                "\nTotal number of DataModel swaps (from Cache to Disk): " + swapsNumber;
    }

    /**
     * save cache memory to disk before server is shut down
     */
    public void updated() {
        Map<Long, DataModel<T>> inRam = algo.getRam();
        for (Long dmKey : inRam.keySet()) {
            idao.save(inRam.get(dmKey));
        }
    }
}
