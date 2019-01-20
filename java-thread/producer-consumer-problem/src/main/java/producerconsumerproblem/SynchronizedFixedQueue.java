package producerconsumerproblem;


public class SynchronizedFixedQueue<T> {

    public static final int DEFAULT_SIZE = 6;

    private Object[] queue;

    private int insert = 0;

    private int remove = 0;

    private int size = 0;

    private int elementCount = 0;

    SynchronizedFixedQueue(int initSize) {
        queue = new Object[initSize];
        size = initSize;
    }

    SynchronizedFixedQueue() {
        this(DEFAULT_SIZE);
    }

    //线程控制与数据结构写在一起了，这个不好。
    //主要是练习，synchronized 与 wait() notifyAll的写法
    public synchronized T poll() {
        try {
            while (elementCount == 0) {
                this.wait();//队列已空，挂起消费者线程，挂起线程后，会释放锁。
            }
            elementCount--;
            T result = (T) queue[remove];
            remove++;
            if(remove == size) remove = 0;
            //通知所有由于这个锁挂起的线程，包括消费者线程，生产者线程
            //主要唤醒生产者线程
            this.notifyAll();
            return result;

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized boolean offer(T t) {
        try {
            while(elementCount == size) this.wait();//已满，挂起生产者线程，挂起线程后，会释放锁。
            elementCount++;
            queue[insert++] = t;
            if (insert == size) insert = 0;
            //通知所有由于这个锁挂起的线程，包括消费者线程，生产者线程
            //主要唤醒消费者线程
            this.notifyAll();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized int getElementCount(){
        return elementCount;
    }

}
