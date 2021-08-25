package com.company;

import java.util.Map;

/**
 * implements cache memory by random priority
 */
public class RandomReplacementAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {

    public RandomReplacementAlgoCacheImpl(int capacity) {
        super(capacity);
    }

    @Override
    public V putElement(K key, V value) {
        putInRam(key, value);

        V retValue = null;

        if (isMemoryFull()) {
            Map<K, V> memory;
            memory = getRam();
            java.util.Random rand = new java.util.Random();
            K[] keys = (K[]) memory.keySet().toArray();
            K randKey = keys[rand.nextInt(keys.length)];
            while (randKey == key) {
                randKey = keys[rand.nextInt(keys.length)];
            }
            retValue = getElement(randKey);
            removeElement(randKey);
        }
        return retValue;
    }
}
