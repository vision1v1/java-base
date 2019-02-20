package threadsimpleusage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSimpleUsageMain {
    public static void main(String[] args) {
        //test01();
        //test02();
        //test03();
        //test04();
        test05();
        end();
    }

    public static void test01() {
        Thread t = new MyThread();
        t.setName("hello-thread");
        t.setDaemon(true);
        t.start();
    }

    public static void test02() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + i);
                }
            }
        });
        t.setDaemon(false);
        t.setName("hello-runnable");
        t.start();
    }

    //测试线程 interrupt
    public static void test03() {

        Thread sleep = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState() + " " + Thread.currentThread().isInterrupted());
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sleep.setName("sleep");
        sleep.start();

        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState() + " " + Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                    //当前线程标记中断后，当前线程在sleep，就会出现 InterruptedException。
                    //Thread.sleep(5000);

                    //当前线程标记中断后，当前线程在join，就会出现 InterruptedException。
                    sleep.join();
                    System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getState() + " " + Thread.currentThread().isInterrupted());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setDaemon(false);
        t.setName("test03-thread");
        t.start();
    }

    //测试UncaughtExceptionHandler
    @SuppressWarnings("Duplicates")
    public static void test04() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println(t.getName() + " 出现异常");
            e.printStackTrace();
        });


        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }
            throw new NullPointerException();
        }, "t1");
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {

            }
            throw new ArrayIndexOutOfBoundsException();
        }, "t2");
        t2.start();


        Thread monitor = new Thread(() -> {
            while(true){
                try {
                    System.out.println(t1.getName()+ ":" +t1.getState() + " " + t2.getName() + ":" +t2.getState());
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        }, "monitor");
        monitor.start();


    }

    //测试线程状态
    //1.NEW 没有调用 start()方法。
    //2.RUNNABLE
    //3.BLOCKED
    //4.WAITING  表示线程挂起
    //5.TIMED_WAITING
    //6.TERMINATED
    @SuppressWarnings("Duplicates")
    public static void test05(){
        Lock lock = new ReentrantLock();

        Object obj1 = new Object();
        Object obj2 = new Object();

        Thread work1 = new Thread(()->{
            //会导致blocked
//            synchronized (obj1){
//                Thread.yield();
//                synchronized (obj2){
//
//                }
//            }

            //WAITING
            LockSupport.park();

            //WAITING
//            lock.lock();
//            try{
//                //TIMED_WAITING
//                Thread.sleep(10000);
//            }
//            catch (Exception e){
//
//            }
        },"work1");

        work1.start();

        Thread work2 = new Thread(()->{
            //BLOCKED
//            synchronized (obj2){
//                Thread.yield();
//                synchronized (obj1){
//
//                }
//            }
            lock.lock();


        },"work2");

        work2.start();

        Thread monitor = new Thread(()->{
            while(true){
                try{
                    System.out.println(work1.getName() + ":" + work1.getState() + " " + work2.getName() + ":" + work2.getState());
                    Thread.sleep(1000);
                }
                catch (Exception e) {

                }
            }
        },"monitor");
        monitor.start();
    }

    public static void end() {
        try {
            int input = System.in.read();
            System.out.println("End" + input);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
