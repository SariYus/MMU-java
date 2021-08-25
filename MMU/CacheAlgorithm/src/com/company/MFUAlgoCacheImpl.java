package com.company;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * implements cache memory according to the MFU algorithm
 * memoryUse : saves the number of accesses per element in memory
 */
public class MFUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {

    private Map<K, Integer> memoryUse;

    /**
     * constructor - initialize variables
     */
    public MFUAlgoCacheImpl(int capacity) {
        super(capacity);
        memoryUse = new HashMap<>();
    }

    /**
     * @return memoryUse map of all elements
     */
    public Map<K, Integer> getMemoryUse() {
        return memoryUse;
    }

    @Override
    public V putElement(K key, V value) {
        putInRam(key, value);
        V retValue = null;
        if (isMemoryFull()) {
            K max = Collections.max(memoryUse.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            retValue = getElement(max);
            removeElement(max);
            putInRam(key, value);
        }
        memoryUse.put(key, 0);
        return retValue;
    }

    @Override
    public V getElement(K key) {
        if (memoryUse.get(key) != null) {
            memoryUse.put(key, memoryUse.get(key).intValue() + 1);
        }
        return super.getElement(key);
    }

    @Override
    public void removeElement(K key) {
        super.removeElement(key);
        memoryUse.remove(key);
    }
}
