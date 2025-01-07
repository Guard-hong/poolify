package cn.poolify.core.utils;

import java.util.*;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/7
 * @Description:
 **/
public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> List<T> newList(T[] args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> list) {
        return !isEmpty(list);
    }
    public static <T,R> HashMap<T,R> newHashMap(){
        return new HashMap<>();
    }
}
