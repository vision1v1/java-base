package limitlatchusage;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class LimitLatch {

    private class Sync extends AbstractQueuedSynchronizer{
        private static final long serialVersionUID = 1L;

        public Sync(){

        }

        @Override
        protected int tryAcquireShared(int arg) {
            long newCount = count.incrementAndGet();
            //超过的上限，并且没有调用过releaseAll方法，返回负数
            //基类会使当前执行线程，进入等待队列。
            if(!released && newCount > limit){
                count.decrementAndGet();
                return -1;
            }
            else{
                return 1;
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            count.decrementAndGet();
            return true;
        }
    }


    private final Sync sync;
    private final AtomicLong count;
    private volatile long limit;
    private volatile boolean released = false;//表示是否全部释放掉了

    public LimitLatch(long limit){
        this.limit = limit;
        this.count = new AtomicLong(0);
        this.sync = new Sync();
    }

    //返回当前锁存的数量
    public long getCount(){
        return count.get();
    }

    //返回上限数
    public long getLimit(){
        return this.limit;
    }

    //设置上限数
    public void setLimit(long limit){
        this.limit = limit;
    }

    //申请共享锁，如果没有共享锁可用，使当前线程等待。
    public void countUpOrAwait() throws InterruptedException{
        sync.acquireSharedInterruptibly(1);
    }

    //释放共享锁，使得其它等待使用这个锁的线程可以使用。
    public long countDown(){
        sync.releaseShared(0);
        long result = getCount();
        return result;
    }

    //释放所有等待的线程并导致limit被忽略，直到调用reset()
    //返回 true 表示释放完成
    public boolean releaseAll(){
        released = true;
        return sync.releaseShared(0);
    }

    //重置 latch
    public void reset(){
        this.count.set(0);
        released = false;
    }

    //如果有一个线程在等待申请共享锁，返回true
    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }

    //返回等待申请共享锁的线程
    public Collection<Thread> getQueuedThreads(){
        return sync.getQueuedThreads();
    }


}
