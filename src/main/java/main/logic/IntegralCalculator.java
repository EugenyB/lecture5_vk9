package main.logic;

import lombok.AllArgsConstructor;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

@AllArgsConstructor
public class IntegralCalculator {
    private double start;
    private double end;
    private int steps;
    private DoubleUnaryOperator function;

    public double calculate() {
        var h = (end - start) / steps;
        return IntStream.range(0, steps).mapToDouble(i -> start + i * h).map(function).map(y -> y * h).sum();
    }
}
