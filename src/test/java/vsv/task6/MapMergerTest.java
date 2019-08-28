package vsv.task6;

import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MapMergerTest {

    @Test
    public void testMerge() {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        map1.put("key1", 20);
        map1.put("key2", 30);
        map2.put("key3", 40);
        map2.put("key1", 50);

        Map<String, Integer> out1 = MapMerger.merge(new HashMap<>(map1), new HashMap<>(map2), Integer::sum);
        assertEquals(3, out1.size());
        assertArrayEquals(new String[]{"key1", "key2", "key3"}, out1.keySet().toArray());
        assertArrayEquals(new Integer[]{70, 30, 40}, out1.values().toArray());

        Map<String, Integer> out2 = MapMerger.merge(new HashMap<>(map1), new HashMap<>(map2), (a, b) -> a * b);
        assertEquals(3, out2.size());
        assertArrayEquals(new String[]{"key1", "key2", "key3"}, out2.keySet().toArray());
        assertArrayEquals(new Integer[]{1000, 30, 40}, out2.values().toArray());

        Map<Integer, String> mapA = new HashMap<>();
        Map<Integer, String> mapB = new HashMap<>();
        mapA.put(1, "A");
        mapA.put(2, "AA");
        mapB.put(1, "B");
        mapB.put(2, "BB");

        Map<Integer, String> out3 = MapMerger.merge(new HashMap<>(mapA), new HashMap<>(mapB), String::concat);
        assertEquals(2, out3.size());
        assertArrayEquals(new Integer[]{1, 2}, out3.keySet().toArray());
        assertArrayEquals(new String[]{"AB", "AABB"}, out3.values().toArray());

        Map<String, Parent> parentMap1 = new HashMap<>();
        Map<String, Parent> parentMap2 = new HashMap<>();
        parentMap1.put("key1", new Parent(20));
        parentMap1.put("key2", new Child(30));
        parentMap2.put("key3", new Child(40));
        parentMap2.put("key1", new Child(50));

        Map<String, Parent> out4 = MapMerger.merge(parentMap1, parentMap2, (p1, p2) -> new Parent(p1.getVal() + p2.getVal()));
        assertEquals(3, out4.size());
        assertArrayEquals(new String[]{"key1", "key2", "key3"}, out4.keySet().toArray());
        assertArrayEquals(new Parent[]{new Parent(70), new Child(30), new Child(40)}, out4.values().toArray());
    }

    @Test
    public void testMergeMapList() {
        Map<String, Integer> map1 = new HashMap<>();
        Map<String, Integer> map2 = new HashMap<>();
        map1.put("key1", 1);
        map1.put("key2", 1);
        map2.put("key3", 1);
        map2.put("key1", 1);
        Map<String, Integer> map3 = new HashMap<>();
        Map<String, Integer> map4 = new HashMap<>();
        map3.put("key1", 1);
        map3.put("key2", 1);
        map3.put("key3", 1);
        map4.put("key1", 1);
        map4.put("key2", 1);
        map4.put("key3", 1);
        Map<String, Integer> map5 = new HashMap<>();
        Map<String, Integer> map6 = new HashMap<>();
        map5.put("key1", 1);
        map5.put("key2", 1);
        map5.put("key3", 1);
        map5.put("key4", 1);
        map6.put("key1", 1);
        map6.put("key2", 1);
        map6.put("key3", 1);
        map6.put("key4", 1);
        Map<String, Integer> map7 = new HashMap<>();
        Map<String, Integer> map8 = new HashMap<>();
        map7.put("key1", 1);
        map7.put("key2", 1);
        map7.put("key3", 1);
        map7.put("key4", 1);
        map7.put("key5", 1);
        map8.put("key1", 1);
        map8.put("key2", 1);
        map8.put("key3", 1);
        map8.put("key4", 1);
        map8.put("key5", 1);
        Map<String, Integer> map9 = new HashMap<>();
        Map<String, Integer> map10 = new HashMap<>();
        map9.put("key1", 1);
        map9.put("key2", 1);
        map9.put("key3", 1);
        map9.put("key4", 1);
        map9.put("key5", 1);
        map9.put("key6", 1);
        map10.put("key1", 1);
        map10.put("key2", 1);
        map10.put("key3", 1);
        map10.put("key4", 1);
        map10.put("key5", 1);
        map10.put("key6", 1);
        List<Map<String, Integer>> mapList = Arrays.asList(map1, map2, map3, map4, map5, map6, map7, map8, map9, map10);

        BinaryOperator<Integer> sumFunc = Integer::sum;
        BinaryOperator<Integer> sumFuncWithPrintThreadName = (i1, i2) -> {
            System.out.println(Thread.currentThread().getName());
            return sumFunc.apply(i1, i2);
        };

        Map<String, Integer> out = MapMerger.mergeMapList(mapList, sumFuncWithPrintThreadName);
        TreeMap<String, Integer> sortedOut = new TreeMap<>(out);
        assertEquals(6, sortedOut.size());
        assertArrayEquals(new String[]{"key1", "key2", "key3", "key4", "key5", "key6"}, sortedOut.keySet().toArray());
        assertArrayEquals(new Integer[]{10, 9, 9, 6, 4, 2}, sortedOut.values().toArray());

        Map<String, Integer> out2 = MapMerger.mergeMapList2(mapList, sumFuncWithPrintThreadName);
        TreeMap<String, Integer> sortedOut2 = new TreeMap<>(out2);
        assertEquals(6, sortedOut2.size());
        assertArrayEquals(new String[]{"key1", "key2", "key3", "key4", "key5", "key6"}, sortedOut2.keySet().toArray());
        assertArrayEquals(new Integer[]{10, 9, 9, 6, 4, 2}, sortedOut2.values().toArray());
    }

    public static class Parent {
        private int val;

        public Parent(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        @Override
        public String toString() {
            return "Parent{" +
                    "val=" + val +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Parent parent = (Parent) o;
            return val == parent.val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    public static class Child extends Parent {
        public Child(int val) {
            super(val);
        }
    }
}
