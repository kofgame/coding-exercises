package threading;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrey on 29.05.2017.
 */
public class PerformanceTesterImplTest {

    @Test
    public void runPerformanceTest() throws InterruptedException {
        int poolSize = 4;
        int fibonacciCalcExecutionAmount = 10;
        CountDownLatch latch = new CountDownLatch(Runtime.getRuntime().availableProcessors());
        PerformanceTesterImpl perfTesterImpl = new PerformanceTesterImpl(latch);

        perfTesterImpl.runPerformanceTest(
                perfTesterImpl.new FibCalcRunner(12),
                fibonacciCalcExecutionAmount,
                poolSize);
    }

}