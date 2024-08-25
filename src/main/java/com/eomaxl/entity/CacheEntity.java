package com.eomaxl.entity;

/**
 * A class representing a single cache entity.
 *
 * @param <K> the type of keys maintained by the cache
 * @param <V> the type of mapped values
 */

public class CacheEntity<K, V> {
    private final K key;
    private final V value;

    public CacheEntity(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
