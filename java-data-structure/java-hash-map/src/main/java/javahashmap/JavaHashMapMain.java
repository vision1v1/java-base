package javahashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;


//1 hash 函数，根据key的内部地址计算出hash值，不同的对象会生成不同的hash值，也可能相同（概率小，哈希冲突）。但是，相同的对象一定会生成相同的hash值。
//2 Entry 记录，Map中存储key value，用于mapping作用。
//3 Node[] 用于存储 Node扩展了 Entry
public class JavaHashMapMain {

    public static void main(String[] args) {

        testMap();

    }


    public static void testMap() {
        Map<String, Integer> ageMaps = new HashMap<String, Integer>();
        ageMaps.put("infi", 20);
        ageMaps.put("th000", 22);
        ageMaps.put("fly100", 23);
        ageMaps.put("ted", 24);

        System.out.println(ageMaps.containsKey("infi"));

        System.out.println("------------------lambda-----------");
        ageMaps.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });

        ageMaps.remove("infi");

        System.out.println("-----------------anonymous---------");
        ageMaps.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String s, Integer integer) {
                System.out.println(s + " : " + integer);
            }
        });

        System.out.println(ageMaps.get("th000"));
        System.out.println(ageMaps.get("fly100"));
        System.out.println(ageMaps.get("ted"));

    }


    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
