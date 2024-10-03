package main.threads;

import main.Main;
import main.logic.IntegralCalculator;

import java.util.function.DoubleUnaryOperator;

public class RunnableCalculator implements Runnable {
    private final IntegralCalculator integralCalculator;
    private final Main main;

    public RunnableCalculator(double start, double end, int steps, DoubleUnaryOperator f, Main main) {
        this.main = main;
        integralCalculator = new IntegralCalculator(start, end, steps, f);
    }

    @Override
    public void run() {
        main.sendResult(integralCalculator.calculate());
    }
}
