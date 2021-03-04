package hellodelayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Order implements Delayed {

    private long expiredTime;
    String name;

    public Order(String name, long time, TimeUnit timeUnit) {
        this.name = name;
        this.expiredTime = System.currentTimeMillis() + (time > 0 ? timeUnit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return this.expiredTime - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        Order order = (Order) o;
        long diff = this.expiredTime - order.expiredTime;
        return diff <= 0 ? -1 : 1;
    }
}
