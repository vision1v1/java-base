package locksupportusage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutex {
    private final AtomicBoolean locked = new AtomicBoolean(false);
    private final Queue<Thread> waiters = new ConcurrentLinkedQueue<>();

    public void lock() {
        boolean wasInterrupted = false;
        Thread current = Thread.currentThread();
        waiters.add(current);

        while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
            LockSupport.park(this);
            if(Thread.interrupted())
                wasInterrupted = true;
        }

        waiters.remove();
        if(wasInterrupted)
            current.interrupt();
    }

    public void unlock(){
        locked.set(false);
        LockSupport.unpark(waiters.peek());
    }
}
