package javaconcurrentskiplistmap;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListMap;

public class JavaConcurrentSkipListMapMain {
    public static void main(String[] args) {
        test01();

    }

    public static void test01() {


//        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap((a,b)->{
//            return (Integer) a- (Integer) b;
//        });

        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap(Comparator.comparingInt(key->(Integer)key));

        for (int i = 0; i < 100; i++) {
            //插入时按照比较器对key排序
            //如果没有指定比较器，按照自然顺序排序。
            //随机抽取key，做分层索引。
            map.put(new Integer(i), "hello " + i);
        }

        map.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });

    }
}
