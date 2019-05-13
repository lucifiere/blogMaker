package com.lucifiere.blogmaker.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 映射工具类
 *
 * @author XD.Wang
 * @date 2017/3/31.
 */
final public class MapUtils {

    private MapUtils() {
    }

    /**
     * 创建String - Obj类型的Map
     *
     * @param objects 参数列表
     * @return 结果
     */
    public static Map<String, Object> createSOMap(Object... objects) {
        if (objects == null) {
            return new HashMap<>(0);
        }
        Map<String, Object> map = new HashMap<>((objects.length + 1) / 2);
        final int step = 2;
        for (int i = 0; i < objects.length; i += step) {
            Object key = objects[i];
            Object value = null;
            if (objects.length > i + 1) {
                value = objects[i + 1];
            }
            if (key == null) {
                map.put(null, value);
            } else if (key instanceof String) {
                map.put((String) key, value);
            } else {
                throw new ClassCastException(key.getClass() + " can not cast to be String");
            }
        }
        return map;
    }

    /**
     * 根据类型和参数创建一个map
     * 如果类型不匹配则抛出异常
     *
     * @param keyType   键的类型
     * @param valueType 值的类型
     * @param objects   参数
     * @param <K>       key gen
     * @param <V>       value gen
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> createCheckedMap(Class<K> keyType, Class<V> valueType, Object... objects) {
        if (objects == null) {
            return new HashMap<>(0);
        }
        Map<K, V> map = new HashMap<>((objects.length + 1) / 2);
        final int step = 2;
        for (int i = 0; i < objects.length; i += step) {
            Object key = objects[i];
            Object value = null;
            if (objects.length > i + 1) {
                value = objects[i + 1];
            }

            if (key != null && !keyType.isAssignableFrom(key.getClass())) {
                throw new ClassCastException(key.getClass() + " can not cast to be " + keyType);
            } else if (value != null && !valueType.isAssignableFrom(value.getClass())) {
                throw new ClassCastException(value.getClass() + " can not cast to be " + valueType);
            }
            map.put((K) key, (V) value);
        }
        return map;
    }

    /**
     * 根据字段给list进行一对一分组（基于反射）
     *
     * @param list       list
     * @param filedClazz 字段的类型，用做运行时检查
     * @param filed      字段
     * @param <K>        K PO分组依据的字段
     * @param <V>        V PO的类型
     * @return 分组结果
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> singleGroupListByFiled(List<V> list, Class<K> filedClazz, String filed) {
        try {
            if (list != null && list.size() > 0) {
                Map<K, V> res = new HashMap<>(list.size() + 1);
                Field fField = list.get(0).getClass().getDeclaredField(filed);
                if (!filedClazz.isAssignableFrom(fField.getType())) {
                    throw new RuntimeException("error key type!");
                }
                fField.setAccessible(true);
                for (V k : list) {
                    Object key = fField.get(k);
                    res.put((K) key, k);
                }
                return res;
            }
            return new HashMap<>(0);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("analysis foreign field failed: " + e.getMessage());
        }
    }

    public interface GetKey<K, V> {

        /**
         * 获取外键
         *
         * @param value 键
         * @return 键
         */
        K getKey(V value);

    }

    private static <K, V> Set<K> keySet(Collection<V> list, GetKey<K, V> func) {
        if (CollectionUtils.isNotEmpty(list)) {
            Set<K> keySet = new HashSet<>();
            for (V value : list) {
                keySet.add(func.getKey(value));
            }
            return keySet;
        }
        return Collections.emptySet();
    }

    /**
     * 根据字段给list进行一对一分组（基于接口）
     *
     * @param list list
     * @param func 获取外键
     * @param <K>  K PO分组依据的字段
     * @param <V>  V PO的类型
     * @return 分组结果
     */
    public static <K, V> Map<K, V> singleGroupByFiled(Collection<V> list, GetKey<K, V> func) {
        if (CollectionUtils.isNotEmpty(list)) {
            Map<K, V> map = new HashMap<>(list.size());
            for (V value : list) {
                map.put(func.getKey(value), value);
            }
            return map;
        }
        return Collections.emptyMap();
    }

    /**
     * 根据字段给list进行一对多分组（基于接口）
     *
     * @param list list
     * @param func 获取外键
     * @param <K>  K PO分组依据的字段
     * @param <V>  V PO的类型
     * @return 分组结果
     */
    public static <K, V> Map<K, List<V>> multiGroupByFiled(List<V> list, GetKey<K, V> func) {
        if (CollectionUtils.isNotEmpty(list)) {
            Map<K, List<V>> map = new HashMap<>(list.size());
            for (V value : list) {
                K key = func.getKey(value);
                List<V> currentValues = map.computeIfAbsent(key, k -> new ArrayList<>());
                currentValues.add(value);
            }
            return map;
        }
        return Collections.emptyMap();
    }

}
