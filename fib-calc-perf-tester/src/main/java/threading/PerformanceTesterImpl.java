package threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * Created by Andrey for fun
 */
public class PerformanceTesterImpl implements PerformanceTester {
    // latch is absolutely redundant here, it's here for just to play with
    private CountDownLatch latch;

    @Override
    public PerformanceTestResult runPerformanceTest(int sumFibNumbersUpto, int executionCount, int threadPoolSize) throws InterruptedException {
        System.out.println("<--- Calculating Fib Numbers upto " + sumFibNumbersUpto +
                ", number of executions / tasks for each fibUptoNumber " + executionCount + " ---> \n");
        latch = new CountDownLatch(executionCount);

        long testStart = System.currentTimeMillis();
        List<Long> calcDurations = new ArrayList<>(executionCount);
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        for (int i = 0; i < executionCount; i++) {
            long calcExecutionStart = System.currentTimeMillis();
            Callable<Long> task = new FibCalcRunner(sumFibNumbersUpto);
            Future<Long> result = executor.submit(task);
            Long particularCalcDuration = null;
            try {
                Long calcResult = result.get(3, TimeUnit.SECONDS);
                particularCalcDuration = System.currentTimeMillis() - calcExecutionStart;
                System.out.println(" " + i + "-th calc iteration took " + particularCalcDuration + " MILLIS");
            } catch (ExecutionException | TimeoutException e) {
                System.err.println(e.getStackTrace());
            }
            calcDurations.add(particularCalcDuration);
            latch.countDown();
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);

        long totalDuration = System.currentTimeMillis() - testStart;
        long minDuration = calcDurations.stream().mapToLong(n -> n).min().getAsLong();
        long maxDuration = calcDurations.stream().mapToLong(m -> m).max().getAsLong();
        PerformanceTestResult testResult = new PerformanceTestResult(totalDuration, minDuration, maxDuration);
        latch.countDown();
        System.out.println(testResult);
        return testResult;
    }

    class FibCalcRunner implements Callable<Long> {
        private int countUpTo;

        public FibCalcRunner(int countUpTo) {
            this.countUpTo = countUpTo;
        }

        @Override
        public Long call() {
            Long calcResult = new FibCalcImpl().fib(countUpTo);
            System.out.print("In async Fib calculation, threadName: " + Thread.currentThread().getName() + " --> ");
            return calcResult;
        }
    }
}