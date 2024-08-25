package com.eomaxl.utilities;

import com.eomaxl.entity.CacheEntity;

public interface Cache<K,V> {
    void put(K key, V value);
    CacheEntity<K, V> get(K key);
    void remove(K key);
    void clear();
    int size();
    boolean containsKey(K key);
}
