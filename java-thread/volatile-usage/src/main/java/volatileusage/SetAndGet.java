package volatileusage;

public class SetAndGet {

    private volatile int a = 0;

    public synchronized int getA() {
        try{
            System.out.println("getA a=" + a);
            //Thread.sleep(1000);
            return a;
        }catch (Exception e){
            return a;
        }
    }

    public synchronized void setA(int a) {
        try{
            this.a = a;
            //Thread.sleep(1000);
            System.out.println("setA a=" + a);
        }catch (Exception e){

        }
    }
}
