package threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;


/**
 * Created by Andrey on 29.05.2017.
 */
public class PerformanceTesterImpl implements PerformanceTester {
    private CountDownLatch latch;

    public PerformanceTesterImpl(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        long testStart = System.currentTimeMillis();
        List<Long> fibCalcDurations = new ArrayList<>(executionCount);
        for (int i = 0; i < executionCount; i++) {
            long fibCalcExecutionStart = System.currentTimeMillis();
            ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
            executor.execute(task);
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.MINUTES);
            fibCalcDurations.add(System.currentTimeMillis() - fibCalcExecutionStart);
        }
//        latch.await();
        long totalDuration = System.currentTimeMillis() - testStart;
        long minDuration = fibCalcDurations.stream().mapToLong(n -> n).min().getAsLong();
        long maxDuration = fibCalcDurations.stream().mapToLong(m -> m).max().getAsLong();
        System.out.println("totalTime: "+ totalDuration + ", minTime: " + minDuration + ", maxTime: " + maxDuration);
        return new PerformanceTestResult(totalDuration, minDuration, maxDuration);
    }

    class FibCalcRunner implements Runnable {
        private int countUpTo;

        public FibCalcRunner(int countUpTo) {
            this.countUpTo = countUpTo;
        }

        @Override
        public void run() {
            System.out.println(currentThread().getName() + " -> FibonacciNumber: " + new FibCalcImpl().fib(countUpTo));
            latch.countDown();
            // System.out.println("Latch count: " + latch.getCount());
        }
    }

}