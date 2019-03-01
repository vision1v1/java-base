package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceMain {
    public static void main(String[] args) {
        //test01();
        //test02();
        test03();
    }

    public static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    //test SoftReference
    public static void test01() {
        SoftReference<Person>[] p = new SoftReference[7000];

        for (int i = 0; i < p.length; i++) {
            p[i] = new SoftReference<>(new Person("name " + i));
        }

        System.out.println(p[1].get());
        System.out.println(p[3].get());

        System.gc();
        System.runFinalization();

        System.out.println("---------");

        System.out.println(p[1].get());
        System.out.println(p[3].get());
    }

    //test WeakReference
    public static void test02() {

        String name = new String("你好，中国");

        WeakReference<String> wr = new WeakReference<>(name);

        name = null;

        System.out.println("before gc " + wr.get());

        System.gc();

        // 应为 wr 弱引用了 name 指向的堆内容（这里称为a）。
        // 在name=null后，jvm就认为a就没有在被引用了。就a没有根了。
        // 所以gc()后，a被清理了。
        System.out.println("after gc " + wr.get());
    }

    //软引用和弱引用可以单独使用，虚引用不能单独使用，虚引用的作用是就跟踪对象被垃圾回收的
    //状态，程序可以通过检测与虚引用关联的虚引用队列是否已经包含了指定的虚引用，从而了解
    //虚引用的对象是否即将被回收。
    public static void test03() {

        String name = new String("该找女朋友了");

        ReferenceQueue<String> rq = new ReferenceQueue<>();

        //必须关联一个引用队列。
        PhantomReference pr = new PhantomReference(name, rq);

        //切断根
        name = null;

        //虚引用是拿不到值的。
        System.out.println("before gc " + pr.get());

        System.gc();
        System.runFinalization();

        System.out.println("after gc " + pr.get());

        System.out.println(rq.poll() == pr);


    }
}
