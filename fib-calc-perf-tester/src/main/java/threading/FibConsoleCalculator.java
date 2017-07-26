package threading;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Andrey on 30.05.2017.
 */
public class FibConsoleCalculator {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter fibonacciNumber calculationCount threadPoolSize (delimited by space)" +
                ", for example: \"12 10 4\" and press Enter..");
        Scanner scanner = new Scanner(System.in);
        int[] userInputs = new int[3];
        for(int i = 0; i < 3; i++) {
            userInputs[i] = scanner.nextInt();
        }

        CountDownLatch latch = new CountDownLatch(Runtime.getRuntime().availableProcessors());
        PerformanceTesterImpl perfTesterImpl = new PerformanceTesterImpl(latch);

        perfTesterImpl.runPerformanceTest(
                perfTesterImpl.new FibCalcRunner(userInputs[0]),
                userInputs[1],  // calculationCount
                userInputs[2]); // threadPoolSize

    }

}