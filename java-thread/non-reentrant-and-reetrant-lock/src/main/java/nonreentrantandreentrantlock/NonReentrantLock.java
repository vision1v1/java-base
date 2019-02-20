package nonreentrantandreentrantlock;

public class NonReentrantLock {

    private boolean isLocked = false;

    private Thread holder;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        holder = Thread.currentThread();
        isLocked = true;
    }

    //由于是synchronized的方法，就不用CAS操纵了
    public synchronized void unLock() {
        if(holder == Thread.currentThread()){
            isLocked = false;
            notify();
        }
    }

}
