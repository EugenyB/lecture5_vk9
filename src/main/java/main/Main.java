package main;

import main.logic.IntegralCalculator;
import main.threads.RunnableCalculator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.DoubleUnaryOperator;

import static java.lang.Math.PI;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        double a = 0;
        double b = PI;
        int n = 1_000_000_000;
        DoubleUnaryOperator f = Math::sin;

//        double res = new IntegralCalculator(a,b,n,f).calculate();
//        System.out.println("res = " + res);

        lock = new ReentrantLock();
        condition = lock.newCondition();

        int nThreads = Runtime.getRuntime().availableProcessors();
        double delta = (b-a)/nThreads;
        total = 0;
        finished = 0;
        for (int i = 0; i < nThreads; i++) {
            double start = a + i * delta;
            double end = start + delta;
            new Thread(new RunnableCalculator(start, end, n/nThreads, f, this)).start();
        }
        lock.lock();
        try {
            while (finished < nThreads) {
                condition.await();
            }
        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
        System.out.println("total = " + total);
    }

    public void sendResult(double res) {
        try {
            lock.lock();
            total += res;
            finished++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    private double total;
    private int finished;

    Lock lock;
    Condition condition;
}
