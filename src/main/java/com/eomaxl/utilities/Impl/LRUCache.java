package com.eomaxl.utilities.Impl;

import java.util.LinkedHashMap;
import java.util.Map;
import com.eomaxl.utilities.Cache;
import com.eomaxl.entity.CacheEntity;

public class LRUCache<K,V> implements Cache<K, V> {
    private final int capacity;
    private final Map<K,V> cacheMap;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cacheMap = new LinkedHashMap<K,V>(capacity, 0.75f, true) {
            private static final long serialVersionUID = 1L;

            @Override
            protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    @Override
    public synchronized void put(K key, V value){
        cacheMap.put(key, value);
    }

    @Override
    public synchronized CacheEntity<K,V> get(K key){
        V value = cacheMap.get(key);
        if (value != null){
            return new CacheEntity<K,V>(key, value);
        }
        return null;
    }

    @Override
    public synchronized int size(){
        return cacheMap.size();
    }

    @Override
    public synchronized void remove(K key){
        cacheMap.remove(key);
    }

    @Override
    public synchronized boolean containsKey(K key){
        return cacheMap.containsKey(key);
    }

    @Override
    public synchronized void clear(){
        cacheMap.clear();
    }

}
