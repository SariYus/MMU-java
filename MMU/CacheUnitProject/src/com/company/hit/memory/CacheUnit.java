package com.company.hit.memory;

import com.company.AbstractAlgoCache;
import com.company.IAlgoCache;
import com.company.MFUAlgoCacheImpl;
import com.company.hit.dm.DataModel;

/**
 * implement cache memory according to the various algorithms, here according to MFU
 * algo : algorithm by which it is implemented
 *
 * @param <T> : type of data model contents
 */
public class CacheUnit<T> extends java.lang.Object {

    private MFUAlgoCacheImpl<Long, DataModel<T>> algo;

    /**
     * constructor
     */
    public CacheUnit(MFUAlgoCacheImpl<Long, DataModel<T>> algo) {
        this.algo = algo;
    }

    /**
     * search data models upon request
     *
     * @param ids : array of data models ids to search
     * @return array of data models and null values for items do not exist
     */
    public DataModel<T>[] getDataModels(java.lang.Long[] ids) {
        DataModel<T>[] dataModels = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            dataModels[i] = algo.getElement(ids[i]);
        }
        return dataModels;
    }

    /**
     * insert data models to cache memory
     *
     * @param dataModels : array of data models to insert
     * @return array of data models taken out to make room, and null values when no item was removed
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] dataModels) {
        DataModel<T>[] dModels = new DataModel[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            dModels[i] = algo.putElement(dataModels[i].getDataModelId(), dataModels[i]);
        }
        return dModels;
    }

    /**
     * remove data models from cache memory
     *
     * @param ids : array of data models ids to remove
     */
    public void removeDataModels(java.lang.Long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            algo.removeElement(ids[i]);
        }
    }
}
