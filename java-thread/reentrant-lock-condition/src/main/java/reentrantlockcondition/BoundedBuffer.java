package reentrantlockcondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    private final Object[] objs;

    private int putptr, takeptr, count;

    public BoundedBuffer(int capcity) {
        objs = new Object[capcity];
    }

    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (count == objs.length)
                notFull.await();
            objs[putptr] = obj;
            putptr = (putptr + 1) % objs.length;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }


    public Object get() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            Object result = objs[takeptr];
            takeptr = (takeptr + 1) % objs.length;
            --count;
            notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ count");
        sb.append(count);
        sb.append(" ]");
        return sb.toString();
    }
}
