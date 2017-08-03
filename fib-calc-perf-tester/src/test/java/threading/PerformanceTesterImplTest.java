package threading;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Andrey on 29.05.2017.
 */
public class PerformanceTesterImplTest {

    @Test
    public void runPerformanceTest() throws InterruptedException, TimeoutException, ExecutionException {
        int fibonacciCalcExecutionAmount = 100;
        int poolSize = 4;

        PerformanceTesterImpl perfTesterImpl = new PerformanceTesterImpl();
        perfTesterImpl.runPerformanceTest(1000, fibonacciCalcExecutionAmount, poolSize);
    }

}