package threading;

/**
 * Created by Andrey on 29.05.2017.
 */
public class FibCalcImpl implements FibCalc {

    @Override
    public long fib(int numb) {
        if (numb == 0 || numb == 1) {
            return numb;
        }
        long fib1 = 1, fib2 = 1, result = 1;
        for (int i = 3; i <= numb; i++) {
            result = fib1 + fib2; // result is sum of previous two Fibonacci number
            fib1 = fib2;
            fib2 = result;
        }
        return result;
    }

}
