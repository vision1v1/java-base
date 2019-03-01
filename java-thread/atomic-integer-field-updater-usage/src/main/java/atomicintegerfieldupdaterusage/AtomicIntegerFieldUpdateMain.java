package atomicintegerfieldupdaterusage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdateMain {

    //这个更新器，用于更新AtomicIntegerFieldUpdateMain 类对象中 字段a的值。
    private static AtomicIntegerFieldUpdater<AtomicIntegerFieldUpdateMain> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicIntegerFieldUpdateMain.class, "a");

    private volatile int a = 10;

    private static volatile boolean isChanged = false;

    public int getA(){
        return a;
    }

    public void setA(int value){
        this.a = value;
    }

    public static void main(String[] args) {

        test01();

        //test02();

    }

    //要完成100个线程，只有一个线程修改字段a
    public static void test01() {

        AtomicIntegerFieldUpdateMain targetObj = new AtomicIntegerFieldUpdateMain();

        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(() -> {
                Thread.yield();
                if(updater.compareAndSet(targetObj,10,20)){
                    Thread.yield();
                    System.out.println("targetObj a 被更新为" + targetObj.getA());
                }
            });

            t.setName("update-thread-" + i);
            t.start();
        }
    }

    //错误的做法
    public static void test02(){
        AtomicIntegerFieldUpdateMain targetObj = new AtomicIntegerFieldUpdateMain();

        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(() -> {
                //下面两行并不是原子的，所以不能保证只有一个线程进去设置值。
                if(!isChanged){
                    Thread.yield();
                    isChanged = true;
                    targetObj.setA(20);
                    System.out.println("targetObj a 被更新为" + targetObj.getA());
                }
            });

            t.setName("update-thread-" + i);
            t.start();
        }
    }


}
