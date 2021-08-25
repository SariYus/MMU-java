package com.company;

import java.util.HashMap;
import java.util.Map;

/**
 * implement IAlgoCache interface
 * ram : cache memory
 * capacity : limit of ram capacity
 */
public abstract class AbstractAlgoCache<K, V> implements IAlgoCache<K, V> {

    private Map<K, V> ram;
    private int capacity;

    /**
     * constructor - initialize variables
     *
     * @param capacity : capacity parameter to initialize
     */
    public AbstractAlgoCache(int capacity) {
        ram = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * @return the whole cache memory
     */
    public Map<K, V> getRam() {
        return ram;
    }

    /**
     * set cache memory
     *
     * @param ram : new ram to change
     */
    public void setRam(Map<K, V> ram) {
        this.ram = ram;
    }

    /**
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * set cache capacity
     *
     * @param capacity : set cache capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public V getElement(K key) {
        return ram.get(key);
    }

    /**
     * Checking use versus capacity
     *
     * @return if cache memory is full
     */
    public boolean isMemoryFull() {
        return (ram.size() > capacity);
    }

    /**
     * put an element in ram
     *
     * @param key   : element key
     * @param value : element content
     */
    public void putInRam(K key, V value) {
        ram.put(key, value);
    }

    @Override
    public void removeElement(K key) {
        ram.remove(key);
    }
}
