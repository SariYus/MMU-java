package com.company;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * implements cache memory according to the LRU algorithm
 * useTime : saves the time of use of each element in memory
 */
public class LRUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {

    private Map<K, Integer> useTime;
    private Integer timeCounter;

    /**
     * constructor - initialize variables
     */
    public LRUAlgoCacheImpl(int capacity) {
        super(capacity);
        useTime = new HashMap<>();
        timeCounter = 0;
    }

    /**
     * @return useTime map of all elements
     */
    public Map<K, Integer> getUseTime() {
        return useTime;
    }

    @Override
    public V putElement(K key, V value) {
        putInRam(key, value);
        V retValue = null;
        if (isMemoryFull()) {
            K min = Collections.min(useTime.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            retValue = getElement(min);
            this.removeElement(min);
            putInRam(key, value);
        }
        useTime.put(key, timeCounter++);
        return retValue;
    }

    @Override
    public V getElement(K key) {
        useTime.put(key, timeCounter++);
        return super.getElement(key);
    }

    @Override
    public void removeElement(K key) {
        super.removeElement(key);
        useTime.remove(key);
    }
}
