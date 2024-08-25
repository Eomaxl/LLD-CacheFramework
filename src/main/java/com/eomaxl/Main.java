package com.eomaxl;

import com.eomaxl.entity.CacheEntity;
import com.eomaxl.manager.CacheManager;
import com.eomaxl.utilities.Cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Get the singleton instance of CacheManager
        CacheManager cacheManager = CacheManager.getInstance();

        // Create a cache with a maximum size of 3
        Cache<String, String> cache = cacheManager.createCache("exampleCache", 3);

        // Populate the cache with some initial data
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        // Demonstrate basic cache operations
        System.out.println("Initial cache state:");
        System.out.println("key1: " + (cache.get("key1") != null ? cache.get("key1").getValue() : "null")); // Outputs: value1
        System.out.println("key2: " + (cache.get("key2") != null ? cache.get("key2").getValue() : "null")); // Outputs: value2
        System.out.println("key3: " + (cache.get("key3") != null ? cache.get("key3").getValue() : "null")); // Outputs: value3

        // Add another entry, which should evict the least recently used entry (key1)
        cache.put("key4", "value4");

        System.out.println("\nCache state after adding key4 (eviction of key1 should occur):");
        System.out.println("key1: " + (cache.get("key1") != null ? cache.get("key1").getValue() : "null")); // Outputs: null (evicted)
        System.out.println("key2: " + (cache.get("key2") != null ? cache.get("key2").getValue() : "null")); // Outputs: value2
        System.out.println("key3: " + (cache.get("key3") != null ? cache.get("key3").getValue() : "null")); // Outputs: value3
        System.out.println("key4: " + (cache.get("key4") != null ? cache.get("key4").getValue() : "null")); // Outputs: value4

        // Multi-threaded access to the cache
        System.out.println("\nStarting multi-threaded cache access...");

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Define a task that accesses and modifies the cache
        Runnable cacheTask = () -> {
            for (int i = 5; i <= 7; i++) {
                String key = "key" + i;
                String value = "value" + i;
                cache.put(key, value);
                System.out.println(Thread.currentThread().getName() + " added " + key + ": " + value);
                System.out.println(Thread.currentThread().getName() + " accessed " + key + ": " + (cache.get(key) != null ? cache.get(key).getValue() : "null"));
            }
        };

        // Execute the cache task in multiple threads
        executorService.submit(cacheTask);
        executorService.submit(cacheTask);
        executorService.submit(cacheTask);

        // Shutdown the executor service
        executorService.shutdown();
        try {
            // Wait for all tasks to complete
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Final state of the cache after multi-threaded operations
        System.out.println("\nFinal cache state after multi-threaded access:");
        for (int i = 1; i <= 7; i++) {
            String key = "key" + i;
            CacheEntity<String, String> entity = cache.get(key);
            System.out.println(key + ": " + (entity != null ? entity.getValue() : "null"));
        }
    }

}