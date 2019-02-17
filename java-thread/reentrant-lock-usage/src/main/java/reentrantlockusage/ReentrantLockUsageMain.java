package reentrantlockusage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockUsageMain {
    public static void main(String[] args) throws Exception {
        //test01();
        //test02();
        test03();
        System.out.println("End");
    }

    public static void print() {
        for (int i = 0; i < 10; i++) {
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + i);
        }
    }

    @SuppressWarnings("Duplicates")
    //可重入
    public static void test01() {

        //ReentrantLock 不是jvm内置的，功能强大一些。
        // 1.6以后synchronized与ReentrantLock类似。
        // 1.5ReentrantLock性能好
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //测试可重入锁，每次调用lock()，ReentrantLock计数器+1, 需要调用相同次数的unlock()。
                //计数器为0后，才会真正释放掉锁。
                lock.lock();
                lock.lock();
                print();
                lock.unlock();
                lock.unlock();
            }
        });
        t1.setName("t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                print();
                lock.unlock();
            }
        });
        t2.setName("t2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("Duplicates")
    //可中断
    public static void test02() {

        //下面两个线程死锁，但是可以中断掉。
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try{
                //申请一个可中断锁，lock1
                lock1.lockInterruptibly();
                Thread.sleep(100);
                lock2.lockInterruptibly();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                //如果当前线程持有这个锁，释放掉
                if(lock1.isHeldByCurrentThread()){
                    lock1.unlock();
                }
                if(lock2.isHeldByCurrentThread()){
                    lock2.unlock();
                }
                System.out.println(Thread.currentThread().getId() + ":线程退出");
            }
        });

        Thread t2 = new Thread(() -> {
            try{
                lock2.lockInterruptibly();
                Thread.sleep(100);
                lock1.lockInterruptibly();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if(lock1.isHeldByCurrentThread()){
                    lock1.unlock();
                }
                if(lock2.isHeldByCurrentThread()){
                    lock2.unlock();
                }
                System.out.println(Thread.currentThread().getId() + ":线程退出");
            }
        });

        t1.start();
        t2.start();

        try {
            Thread.sleep(5000);
            //t2.interrupt();//中断死锁的线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //调试reentrant lock源代码
    @SuppressWarnings("Duplicates")
    public static void test03(){
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //测试可重入锁，每次调用lock()，ReentrantLock计数器+1, 需要调用相同次数的unlock()。
                //计数器为0后，才会真正释放掉锁。
                lock.lock();
                lock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    Thread.sleep(20000);
                }
                catch (Exception e){

                }
                lock.unlock();
                lock.unlock();
            }
        });
        t1.setName("t1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState());
                    //拿锁的时间多一些，方便调试源代码。
                    Thread.sleep(60*60*1000);
                }
                catch (Exception e){

                }

                lock.unlock();
            }
        });
        t2.setName("t2");
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
