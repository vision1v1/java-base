package cyclicbarrierusage;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierUsageMain {
    public static void main(String[] args) {

        //功能调用barrier.await()的线程数量到达2后在唤醒线程执行。
        //barrier可以反复使用。
        CyclicBarrier barrier = new CyclicBarrier(2);

        Work w1 = new Work(barrier, "w1");

        Work w2 = new Work(barrier, "w2");

        w1.start();
        w2.start();

        end();
    }

    public static void end(){
        try{
            int key = System.in.read();
            System.out.println("main end " + key);
        }
        catch (Exception e){

        }
    }

    static class Work extends Thread {

        private CyclicBarrier barrier;

        public Work(CyclicBarrier barrier, String threadName) {
            this.barrier = barrier;
            setName(threadName);
        }

        @Override
        public void run() {
            try{
                Thread currentThread = Thread.currentThread();
                System.out.println(currentThread.getName() + " " + currentThread.getState() + " begin");
                Thread.sleep(2000);
                barrier.await();
                System.out.println(currentThread.getName() + " " + currentThread.getState() + " end");
            }
            catch (Exception e){

            }
        }
    }
}
