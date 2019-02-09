package locksupportusage;

import java.io.IOException;
import java.util.concurrent.locks.LockSupport;

public class LockSupportUsageMain {
    public static void main(String[] args) {

        //testPartAndUnpart();

        testParkAndUnparkWithBlocker();

        //testFIFOMutex();

        end();
    }

    public static void testPartAndUnpart() {
        Thread mainThread = Thread.currentThread();

        Thread other = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(mainThread.getName() + " " + mainThread.getState() + " isInterrupt " + mainThread.isInterrupted() + " isAlive " + mainThread.isAlive());
                    Thread.sleep(5000);
                    LockSupport.unpark(mainThread);
                    System.out.println(Thread.currentThread().getName() + " is Done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        other.setName("other");
        other.start();

        System.out.println(mainThread.getName() + " " + mainThread.getState());
        // 阻塞当前线程。
        // 停止当前线程的调度，除非获取调度许可。
        // 如果有调度许可，该方法立即返回。
        LockSupport.park();
        System.out.println(mainThread.getName() + " " + mainThread.getState());
    }

    public static void testParkAndUnparkWithBlocker(){
        String blocker = "Blocker";

        Thread t1 = new Thread(()->{
            try{
                System.out.println("t1 is running");
                LockSupport.park(blocker);
                Thread.sleep(1000);
                System.out.println("t1 is done");
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        },"t1");
        Thread t2 = new Thread(()->{
            try{
                Thread.sleep(2000);
                Object t1_blocker = LockSupport.getBlocker(t1);
                System.out.println("t1's blocker is " + t1_blocker);
                LockSupport.unpark(t1);
                System.out.println("t2 is done");
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        },"t2");

        t1.start();
        t2.start();
    }


    public static void testFIFOMutex() {

        FIFOMutex fifoMutex = new FIFOMutex();

        Thread t1 = new Thread(new Work(1000,fifoMutex), "t1");
        Thread t2 = new Thread(new Work(2000,fifoMutex), "t2");

        t1.start();
        t2.start();

    }

    public static class Work implements Runnable {

        private FIFOMutex mutex;
        private final int delay;

        Work(int delay,FIFOMutex mutex){
            this.mutex = mutex;
            this.delay = delay;
        }

        @Override
        public void run() {
            try{
                this.mutex.lock();
                Thread curentThread = Thread.currentThread();
                System.out.println(curentThread.getName() + " " + curentThread.getState() + " is working");
                Thread.sleep(delay);
                System.out.println(curentThread.getName() + " " + curentThread.getState() + " is done");
                this.mutex.unlock();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }


        }
    }


    public static void end(){
        try{
            int data = System.in.read();
            System.out.println("End " + data);
        }
        catch (IOException e){

        }


    }
}
