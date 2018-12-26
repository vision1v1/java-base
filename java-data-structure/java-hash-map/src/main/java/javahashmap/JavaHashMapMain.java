package javahashmap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;


//1 hash 函数，根据key的内部地址计算出hash值，不同的对象会生成不同的hash值，也可能相同（概率小，哈希冲突）。但是，相同的对象一定会生成相同的hash值。
//2 Entry 记录，Map中存储key value，用于mapping作用。
//3 Node[] 用于存储 Node扩展了 Entry
public class JavaHashMapMain {

    public static void main(String[] args) {

        //testMap();

        //ConcurrentHashMap 与 SynchronizedMap 区别

        //1.多个线程可以从ConcurrentHashMap中删除键值对, 而在SynchronizedMap情况下, 只允许有一个线程进行更改。这将导致在ConcurrentHashMap实现更高的并发性。

        //2.无需锁定map即可读取ConcurrentHashMap中的值。检索操作将返回由最近完成的插入操作插入的值。在SynchronizedMap中, 读取操作也需要锁。

        //3.如果一个线程尝试修改它, 而另一个线程在它上迭代, ConcurrentHashMap 不会引发并发修改异常。迭代器反映hash map创建时的状态。SynchronizedMap返回迭代器, 它在并发修改时会立即失败。

        testConcurrentHashMap();

        testSynchronizedMap();

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

        System.out.println("-----------------iterator----------");

        Iterator<String> keyIterator = ageMaps.keySet().iterator();
        while (keyIterator.hasNext()){
            String key = keyIterator.next();
            System.out.println(key + " : " + ageMaps.get(key));
        }

        System.out.println("-----------------iterator forEachRemaining----------");

        keyIterator = ageMaps.keySet().iterator();

        keyIterator.forEachRemaining(item->{
            System.out.println(item + " : " + ageMaps.get(item));
        });

        System.out.println("-----------------get----------");

        System.out.println(ageMaps.get("th000"));
        System.out.println(ageMaps.get("fly100"));
        System.out.println(ageMaps.get("ted"));

    }

    public static void testConcurrentHashMap(){
        ConcurrentHashMap<String,Integer> ageMaps = new ConcurrentHashMap<>();
        ageMaps.put("infi", 20);
        ageMaps.put("th000", 22);
        ageMaps.put("fly100", 23);
        ageMaps.put("ted", 24);

        Iterator<String> iterator = ageMaps.keySet().iterator();

        synchronized (ageMaps){
            while (iterator.hasNext()){
                String key = iterator.next();
                System.out.println(key + " : " + ageMaps.get(key));
            }
        }
    }


    public static void testSynchronizedMap(){
        Map<Integer, String> syncHashMap = Collections.synchronizedMap(new HashMap<>());

        //Put require no synchronization
        syncHashMap.put(1, "A");
        syncHashMap.put(2, "B");

        //Get require no synchronization
        syncHashMap.get(1);

        Iterator<Integer> itr = syncHashMap.keySet().iterator();

        //Using synchronized block is advisable
        synchronized (syncHashMap)
        {
            while(itr.hasNext()) {
                System.out.println(syncHashMap.get(itr.next()));
            }
        }
    }



    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
}
