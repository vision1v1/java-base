package javacopyonwritearraylist;

import java.util.concurrent.CopyOnWriteArrayList;

public class JavaCopyOnWriteArrayListMain {

    public static void main(String[] args) {
        test01();
    }

    public static void test01() {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

        for (int i = 0; i < 1000; i++) {
            //写入时线程安全的，但是发生全数组拷贝
            copyOnWriteArrayList.add(new Integer(i));
        }

        copyOnWriteArrayList.forEach(e->{
            System.out.println(e);
        });
    }
}
