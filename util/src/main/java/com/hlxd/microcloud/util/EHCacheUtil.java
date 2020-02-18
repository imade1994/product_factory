package com.hlxd.microcloud.util;



import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import java.util.List;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2019/6/2811:25
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT zcjt-cloud
 */
public class EHCacheUtil {
    /**
     * 缓存管理器
     */
    private static CacheManager cacheManager;


    private EHCacheUtil() {
        // 私有化构造方法
    }

    // 静态代码块,保证singleton。
    static {
        cacheManager = CacheManager.create();
    }

    /**
     * 根据缓存名字获得某个缓存
     *
     * @param cacheName
     * @return
     */
    public static Cache getCache(String cacheName) {
    if (cacheManager.getStatus().equals(Status.STATUS_ALIVE)) {
        return cacheManager.getCache(cacheName);
    }else{
        return CacheManager.create().getCache(cacheName);
    }
    }


    /**
     * 根据缓存名字,元素的key值,获得缓存中对应的value值对象。
     *
     * @param cacheName
     * @param key
     * @param isRemoveKey
     * @return
     */
    public static Object getValue(String cacheName, Object key,
                                  boolean isRemoveKey) {

        Cache cache = getCache(cacheName);
        Element e = null;
        if (isRemoveKey) {
            e = cache.get(key);
        } else {
            e = cache.getQuiet(key);
        }
        if (e == null) {
            return null;
        }
        return e.getObjectValue();
    }

    /**
     * 根据缓存名字,元素的key值,获得缓存中对应的value值对象。
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static Object getValue(String cacheName, Object key) {
        return getValue(cacheName, key, false);
    }

    /**
     * 静态的获取元素，不会产生update.
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static Element getElementByQuite(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        return cache.getQuiet(key);
    }

    /**
     * 动态的获取元素，会产生update.
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static Element getElementByDynic(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        return cache.get(key);
    }

    /**
     * 向某个缓存中添加元素
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public static void put(String cacheName, Object key, Object value) {
        Element element = new Element(key, value);
        //getCache(cacheName).put(element);
        Cache cache = getCache(cacheName);
        cache.put(element);
        cache.flush();
        //cacheManager.shutdown();
    }
    /***
     * 替换缓存中某个元素
     * */
    public static void replaceElement(String cacheName,Object key,Object oldValue,Object newValue){
        Element oldElement = new Element(key, oldValue);
        Element newElement = new Element(key, newValue);
        Cache cache = getCache(cacheName);
        cache.replace(oldElement,newElement);
    }

    /**
     * 移除某个缓存中的元素
     *
     * @param cacheName
     * @param key
     */
    public static void remove(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.remove(key);
        }
        cache.flush();
    }

    /**
     * 移除某个缓存中所有的元素
     *
     * @param cacheName
     */
    public static void removeAll(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            for (Object object : cache.getKeys()) {
                cache.remove(object);
            }
        }
        cache.flush();
    }

    /**
     * 判断某个缓存是否包含某个元素
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static boolean contains(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        Element e = cache.get(key);
        if (e != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取某个缓存中所有的key
     *
     * @param cacheName
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getKeys(String cacheName) {
        Cache cache = getCache(cacheName);
        return (List<T>) cache.getKeys();
    }

    /**
     * 获取某个缓存中所有的value
     *
     * @param cacheName
     * @param
     * @return
     */
   /* @SuppressWarnings("unchecked")
    public static <T> List<T> getValues(String cacheName,List<T> T) {
        Cache cache = getCache(cacheName);
        Map<Object,Element> all = cache.getAll(cache.getKeys());
        Map<String,List<T>> map = new HashMap<>();
        List<Object> objects = (List<Object>) all.keySet();
        for(Object object:objects){
            Element element = all.get(object);
            element.get
        }
        return (List<T>) cache.getKeys();
    }*/

    public static void main(String[] args) {
        String key = "hello";
        String value = "world";
        EHCacheUtil.put("articleCache", key, value);
        System.out.println(EHCacheUtil.getValue("articleCache", key));
    }
}
