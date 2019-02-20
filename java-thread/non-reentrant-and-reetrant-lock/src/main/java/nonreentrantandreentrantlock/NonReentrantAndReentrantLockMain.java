package nonreentrantandreentrantlock;

public class NonReentrantAndReentrantLockMain {
    public static void main(String[] args) {
        //test01();

        test02();
        end();
    }

    @SuppressWarnings("Duplicates")
    public static void test01() {
        NonReentrantLock lock = new NonReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }

        },"t1");

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }

        },"t2");

        t1.start();
        t2.start();
    }

    @SuppressWarnings("Duplicates")
    public static void test02() {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
                lock.unLock();
            }

        },"t1");

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }

        },"t2");

        t1.start();
        t2.start();
    }

    public static void end(){
        try{
            int key = System.in.read();
            System.out.println("End " + key);
        }
        catch (Exception e){

        }
    }
}
