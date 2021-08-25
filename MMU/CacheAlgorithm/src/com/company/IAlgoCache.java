package com.company;

/**
 * cache interface
 *
 * @param <K> : key of the entity in the cache memory
 * @param <V> : value of the entity in the cache memory
 */
public interface IAlgoCache<K, V> {

    /**
     * get element from cache
     *
     * @param key : element key
     * @return element with key = key
     */
    public V getElement(K key);

    /**
     * put element in cache
     *
     * @param key   : element key
     * @param value : element content
     * @return null If there is room in cache, and element removed from cache to make room, otherwise.
     */
    public V putElement(K key, V value);

    /**
     * remove element from cache
     *
     * @param key : element key
     */
    public void removeElement(K key);
}
