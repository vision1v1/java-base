package futuretaskusage;

import java.util.concurrent.Callable;

public class MultiplyingTask implements Callable<Integer> {
    int a;
    int b;
    long sleepTime;

    public MultiplyingTask(int a, int b, long sleepTime) {
        this.a = a;
        this.b = b;
        this.sleepTime = sleepTime;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(sleepTime);
        return a * b;
    }
}
