package com.ahao.commons.util.lang;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ahaochan on 2017/8/6.
 * 集合的操作工具类
 */
public class CollectionHelper {
    /**
     * 获取集合长度, 防止空指针
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }
    public static int size(Map<?, ?> map) {
        return map == null ? 0 : map.size();
    }
    public static int size(Enumeration<?> enumeration) {
        return enumeration == null ? 0 : CollectionUtils.size(enumeration);
    }

    /**
     * 往 collection 集合中添加元素
     * @param collection 集合
     * @param elements   变长数组的元素
     * @param <T>        集合元素类型
     * @return 任意一个添加成功返回true
     */
    @SafeVarargs
    public static <T> boolean add(Collection<T> collection, T... elements) {
        return collection != null && Collections.addAll(collection, elements);
    }

    /**
     * 将数组元素变为 List 集合类型
     * @param elements 所有元素
     * @return 集合, 一般为 ArrayList
     */
    @SafeVarargs
    public static <T> List<T> toList(T... elements) {
        if (ArrayUtils.isEmpty(elements)) {
            return new ArrayList<>();
        }
        return Arrays.stream(elements).collect(Collectors.toList());
    }

    /**
     * 将数组元素变为 Set 集合类型
     * @param elements 所有元素
     * @return 集合, 一般为 HashSet
     */
    public static <T> Set<T> toSet(T... elements) {
        if (ArrayUtils.isEmpty(elements)) {
            return new HashSet<>();
        }
        return Arrays.stream(elements).collect(Collectors.toSet());
    }

    /**
     * 返回子集合, 兼容越界情况
     * @param list                集合
     * @param startIndexInclusive 开始位置, 包括
     * @param endIndexExclusive   结束位置, 不包括
     * @return 子集合
     */
    public static <T> List<T> subList(List<T> list, int startIndexInclusive, int endIndexExclusive) {
        return list == null ? Collections.emptyList() : list.subList(Math.max(0, startIndexInclusive), Math.min(list.size(), endIndexExclusive));
    }

    /**
     * collection 集合是否存在元素 elements
     * @param collection 集合
     * @param elements   元素
     * @return 若集合为null, 返回false, 若集合中存在元素, 返回true, 若集合中不存在元素, 返回false
     */
    public static <T> boolean contains(Collection<? super T> collection, T elements) {
        return !CollectionUtils.isEmpty(collection) && collection.contains(elements);
    }

    /**
     * 检测 集合 是否包含 任意一个值
     * @param collection 集合
     * @param search 待查找的值
     * @return 如果集合为空, 则返回false. 包含任意一个search, 则返回true.
     */
    public static <T> boolean containAny(Collection<T> collection, T... search) {
        if (CollectionUtils.isEmpty(collection)) {
            return false;
        }
        for (T element : search) {
            if (contains(collection, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对List中的Map进行压缩, 只保留keys中的键值对
     * @param maps 集合
     * @param keys 键
     * @return 压缩后的Map
     */
    public static <M extends Map<K, V>, K, V> List<M> retain(List<M> maps, K... keys) {
        return CollectionUtils.isEmpty(maps) ? Collections.emptyList() :
                maps.stream()
                        .peek(m -> m.keySet().retainAll(toList(keys)))
                        .collect(Collectors.toList());
    }

    /**
     * 获取第一个子元素
     * @param collection 集合
     */
    public static <T> T getFirst(Collection<T> collection) {
        return CollectionUtils.isEmpty(collection) ? null : collection.iterator().next();
    }
}