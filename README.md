# Java Caching Framework

## Overview

This Java caching framework is designed to provide a robust and scalable caching mechanism, suitable for various use cases, including multi-threaded environments. The framework features a flexible and generic API, allowing developers to easily integrate caching into their Java applications. It includes a Least Recently Used (LRU) cache implementation and adheres to SOLID design principles and common design patterns such as Singleton and Factory Method.

## Features

- **Generic Cache Interface**: Defines basic operations like `put`, `get`, `remove`, and `clear`.
- **LRU Cache Implementation**: Automatically evicts the least recently used entries when the cache reaches its capacity.
- **Thread-Safe Operations**: The cache is designed to handle concurrent access in multi-threaded environments.
- **Singleton Cache Manager**: Manages the lifecycle of cache instances, ensuring that each cache is unique and easily retrievable.
- **Extensibility**: The framework can be extended with custom cache implementations or eviction policies.

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>cache-framework</artifactId>
    <version>1.0.0</version>
</dependency>
```
### Direct JAR Inclusion
If you have the JAR file, add it to your project's libs directory and include it in your build configuration:
```groovy
dependencies {
    implementation files('libs/cache-framework-1.0.0.jar')
}
```

### Usage
1. Initialize the Cache Manager
   The CacheManager is a singleton class that manages all caches:<br>
    ```
    CacheManager cacheManager = CacheManager.getInstance();

   ```
2. Create a Cache
   Create a new cache with a specified capacity:<br>
    ```java
    Cache<String, String> cache = cacheManager.createCache("myCache", 100);
    ```
3. Put Data into the Cache
   Add data to the cache:<br>
    ```java
   cache.put("key1", "value1");
    cache.put("key2", "value2");
   ```
4. Retrieve Data from the Cache
   Retrieve data from the cache:
    ```java
   CacheEntity<String, String> entity = cache.get("key1");
    if (entity != null) {
    System.out.println("Retrieved value: " + entity.getValue()); // Outputs: value1
    } else {
    System.out.println("Key not found in cache.");
    }
    ```
5.  Remove Data from the Cache
    Remove a specific entry from the cache:<br>
    ```java
    cache.remove("key1");
    ```
6. Clear the Cache
   Clear all entries from the cache:
    ```java
   cache.clear();
   ```
7. Multi-Threaded Access
   The cache framework is thread-safe, so you can safely access it from multiple threads:
    ```java
    Runnable cacheTask = () -> {
    cache.put("threadKey", "threadValue");
    CacheEntity<String, String> entity = cache.get("threadKey");
    if (entity != null) {
        System.out.println(Thread.currentThread().getName() + " retrieved: " + entity.getValue());
    }
    };
    
    Thread thread1 = new Thread(cacheTask);
    Thread thread2 = new Thread(cacheTask);
    
    thread1.start();
    thread2.start();
    
    thread1.join();
    thread2.join();
    ```
### Singleton Pattern:

The CacheManager class is implemented as a singleton, ensuring that only one instance of the cache manager exists throughout the application.
```java
public static CacheManager getInstance() {
return Holder.INSTANCE;
}
```

### Factory Method Pattern:

The CacheManager class uses the Factory Method pattern to create cache instances. The createCache method encapsulates the object creation process.
```java
public <K, V> Cache<K, V> createCache(String cacheName, int size) {
Cache<K, V> cache = new LRUCache<>(size);
caches.put(cacheName, cache);
return cache;
}
```

### Contributing
Contributions are welcome! Please feel free to submit a pull request or open an issue.

### License
This project is licensed under the MIT License - see the LICENSE file for details.