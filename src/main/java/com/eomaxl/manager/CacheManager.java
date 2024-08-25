package com.eomaxl.manager;

import com.eomaxl.utilities.Cache;
import com.eomaxl.utilities.Impl.LRUCache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheManager {
    private final ConcurrentHashMap<String, Cache<?,?>> caches;

    private CacheManager(){
        caches = new ConcurrentHashMap<>();
    }

    private static class Holder {
        private static final CacheManager INSTANCE = new CacheManager();
    }

    public static CacheManager getInstance(){
        return Holder.INSTANCE;
    }

    public <K,V> Cache<K,V> createCache(String cacheName, int size){
        if (caches.containsKey(cacheName)){
            throw new RuntimeException("Cache already exists: " + cacheName);
        }
        Cache<K,V> cache = new LRUCache<>(size);
        caches.put(cacheName, cache);
        return cache;
    }

    public <K,V> Cache<K,V> getCache(String cacheName){
        return (Cache<K,V>) caches.get(cacheName);
    }

    public void removeCache(String cacheName){
        caches.remove(cacheName);
    }
}
