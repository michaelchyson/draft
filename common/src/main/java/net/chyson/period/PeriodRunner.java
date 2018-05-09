package net.chyson.period;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class PeriodRunner {
    private int corePoolSize = 3;
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(corePoolSize);


    public PeriodRunner() {
    }

    public PeriodRunner(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        this.executorService = Executors.newScheduledThreadPool(corePoolSize);
    }

    public void scheduleAtFixedRate(Runnable task, long initialDelay, long period, TimeUnit timeUnit) {
        executorService.scheduleAtFixedRate(task, initialDelay, period, timeUnit);

    }


    public void scheduleAtFixedRate(Runnable task, long period) {
        scheduleAtFixedRate(task, 0L, period, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        new PeriodRunner();
        TimeUnit seconds = TimeUnit.SECONDS;
    }


}
