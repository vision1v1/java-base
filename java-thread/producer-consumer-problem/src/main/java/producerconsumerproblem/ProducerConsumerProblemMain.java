package producerconsumerproblem;


import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerProblemMain {
    public static void main(String[] args) throws Exception {


        //test01();
        test02();
        //while (Thread.activeCount() > 2) Thread.yield();

        int a = System.in.read();

        System.out.println("Ended " + a);

    }

    private static void test02(){
        ProducerConsumerProblemMain pc = new ProducerConsumerProblemMain();
        Thread p1 = new Thread(pc.new Producer01());
        p1.setName("p1");
        Thread p2 = new Thread(pc.new Producer01());
        p2.setName("p2");
        Thread p3 = new Thread(pc.new Producer01());
        p3.setName("p3");
        Thread p4 = new Thread(pc.new Producer01());
        p4.setName("p4");

        Thread c1 = new Thread(pc.new Consumer01());
        c1.setName("c1");

        Thread c2 = new Thread(pc.new Consumer01());
        c2.setName("c2");


        p1.start();
        p2.start();
        //p3.start();
        //p4.start();

        c1.start();
        //c2.start();
    }

    private FixedQueue<Product> fixedQueue = new FixedQueue<>();

    class Consumer01 implements Runnable{

        @Override
        public void run() {
            while(true){
                try{
                    synchronized (fixedQueue){
                        while(fixedQueue.isEmpty()){
                            fixedQueue.wait();
                        }
                        Product p = fixedQueue.poll();
                        if(p!=null){
                            System.out.println(Thread.currentThread().getName() + "消费者，队列总数" + fixedQueue.getElementCount());
                            fixedQueue.notify();//通知生产
                        }
                        Thread.sleep(1000);
                    }
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer01 implements Runnable{

        @Override
        public void run() {
            while (true){
                try{
                    int i = 0;
                    synchronized (fixedQueue){
                        while(fixedQueue.isFull()){
                            fixedQueue.wait();
                        }

                        Product p = new Product("" + i);
                        if(fixedQueue.offer(p)){
                            System.out.println(Thread.currentThread().getName() + "生产者，队列总数" + fixedQueue.getElementCount());
                            fixedQueue.notify();//通知消费
                        }
                        Thread.sleep(500);
                    }
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }






    private static void test01() {
        ProducerConsumerProblemMain pc = new ProducerConsumerProblemMain();
        Thread p1 = new Thread(pc.new Producer());
        p1.setName("p1");
        Thread p2 = new Thread(pc.new Producer());
        p2.setName("p2");
        Thread p3 = new Thread(pc.new Producer());
        p3.setName("p3");
        Thread p4 = new Thread(pc.new Producer());
        p4.setName("p4");

        Thread c1 = new Thread(pc.new Consumer());
        c1.setName("c1");

        Thread c2 = new Thread(pc.new Consumer());
        c2.setName("c2");


        p1.start();
        p2.start();
        //p3.start();
        //p4.start();

        c1.start();
        //c2.start();
    }

    private final SynchronizedFixedQueue<Product> productQ = new SynchronizedFixedQueue<>();

    class Producer implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (true) {
                Product p = new Product(Thread.currentThread().getName() + " " + i++);
                if (productQ.offer(p)) {
                    System.out.println("生产 " + p + "队列元素个数 " + productQ.getElementCount());
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                Product p = productQ.poll();
                if (p != null) {
                    System.out.println("消费 " + p + "队列元素个数 " + productQ.getElementCount());
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
