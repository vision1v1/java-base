package hellodelayqueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
    public static void main(String[] args) throws InterruptedException {
        String timePattern = "yyyy-MM-dd HH:mm:ss";
        Order order1 = new Order("Order1", 5, TimeUnit.SECONDS);
        Order order2 = new Order("Order2", 10, TimeUnit.SECONDS);
        Order order3 = new Order("Order3", 15, TimeUnit.SECONDS);
        DelayQueue<Order> delayQueue = new DelayQueue<>();
        delayQueue.put(order1);
        delayQueue.put(order2);
        delayQueue.put(order3);

        System.out.println("订单延迟队列开始时间：" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(timePattern)));
        while (delayQueue.size() != 0) {
            Order order = delayQueue.poll();
            if (order != null) {
                // 可以实时删除
                // boolean removed = delayQueue.removeIf(o -> o.name == "Order3");
                // System.out.format("Order3 removed : {%s}\n",removed);
                System.out.format("订单：{%s}被取消，取消时间：{%s}\n", order.name, LocalDateTime.now().format(DateTimeFormatter.ofPattern(timePattern)));
            }
            Thread.sleep(1000);
        }
    }
}
