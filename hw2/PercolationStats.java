package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] samples;
    private int N;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        samples = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation sim = pf.make(N);
            double openSquare = 0;

            while (!sim.percolates()) {
                int rand1 = StdRandom.uniform(N);
                int rand2 = StdRandom.uniform(N);
                if (!sim.isOpen(rand2, rand1)) {
                    sim.open(rand2, rand1);
                    openSquare += 1;
                }
            }
            samples[i] = (double) openSquare / (double) (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(samples);
    }

    public double stddev() {
        return StdStats.stddev(samples);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt((double) T);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt((double) T);
    }

}

