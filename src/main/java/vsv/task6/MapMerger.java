package vsv.task6;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class MapMerger {

    /**
     * Efficient merge for two maps when one map is much bigger than the other one.
     * Return one map is much bigger of arguments, also argument will affected.
     */
    public static <K, V> Map<K, V> merge(Map<K, V> map1, Map<K, V> map2, BinaryOperator<V> merger) {
        Map<K, V> outBiggerMap;
        Map<K, V> smallerMap;
        if (map1.size() >= map2.size()) {
            outBiggerMap = map1;
            smallerMap = map2;
        } else {
            outBiggerMap = map2;
            smallerMap = map1;
        }
        smallerMap.forEach((key, value) -> outBiggerMap.merge(key, value, merger));
        return outBiggerMap;
    }

    /**
     * Merge list of maps by Stream API
     */
    public static <K, V> Map<K, V> mergeMapList(List<Map<K, V>> mapList, BinaryOperator<V> merger) {
        return mapList.parallelStream().flatMap(m -> m.entrySet().stream()).collect(Collectors.toConcurrentMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                merger
                )
        );
    }

    /**
     * Merge list of maps by Stream API and first function merge of this class
     */
    public static <K, V> Map<K, V> mergeMapList2(List<Map<K, V>> mapList, BinaryOperator<V> merger) {
        mapList.sort((l1, l2) -> Integer.compare(l2.size(), l1.size()));
        Map<K, V> out = new ConcurrentHashMap<>(mapList.get(0));
        mapList.parallelStream().skip(1).forEach(map -> merge(out, map, merger));
        return out;
    }
}
