package threading;

import java.util.concurrent.Callable;

public interface PerformanceTester {
	
	/**
	 * Runs a performance test of the given task.
	 * @param sumFibNumbersUpto up to which number Fibonacci numbers should be added/summed
	 * @param executionCount how many times the task should be executed in total
	 * @param threadPoolSize how many threads to use
	 */
	public PerformanceTestResult runPerformanceTest(int sumFibNumbersUpto, int executionCount, int threadPoolSize)
			throws InterruptedException;
}
