package reentrantlockusage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockUsageMain {
    public static void main(String[] args) throws Exception{
        test01();
        int r = System.in.read();
        System.out.println("End" + r);
    }

    @SuppressWarnings("Duplicates")
    public static void test01(){

        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                for(int i =0;i<10;i++){
                    Thread.yield();
                    System.out.println(Thread.currentThread().getName() + i);
                }
                lock.unlock();
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                for(int i =0;i<10;i++){
                    System.out.println(Thread.currentThread().getName() + i);
                }
                lock.unlock();
            }
        });
        t2.setName("t2");

        t1.start();
        t2.start();
    }
}
