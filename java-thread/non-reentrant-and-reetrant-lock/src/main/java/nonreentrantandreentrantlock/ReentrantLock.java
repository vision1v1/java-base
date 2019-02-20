package nonreentrantandreentrantlock;

public class ReentrantLock {
    private boolean isLocked = false;
    private Thread holder;
    private int count = 0;

    public synchronized void lock() throws InterruptedException{
        Thread current = Thread.currentThread();
        while (isLocked && holder != current){
            wait();
        }
        isLocked = true;
        holder = current;
        count++;
    }

    public synchronized void unLock(){
        Thread current = Thread.currentThread();
        if(current == holder){
            count--;
            if(count==0){
                isLocked = false;
                holder = null;
                notify();
            }
        }
    }


}
