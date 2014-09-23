/**----------------------------------------------------------------
 *  Author:        Dennis Angeline
 *  Written:       9/19/14
 *  Last updated:  9/19/14
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats GridSize Repetitions
 *  
 *  Programming Assignment 1 for Algs4
 *
 *----------------------------------------------------------------**/

public class PercolationStats 
{
    private int gridSize;
    private int repetitions;
    private double[] openSiteRatio;
    
    
    public PercolationStats(int N, int T) 
    {
        if (N < 1) {
            throw new IllegalArgumentException("Illegal argument value");
        }

        if (T < 1) {
            throw new IllegalArgumentException("Illegal argument value");
        }

        gridSize = N;
        repetitions = T;
        openSiteRatio = new double[repetitions];
        
        for (int i = 0; i < repetitions; i++)
        {
            double openSites = 0;
            
            Percolation p = new Percolation(gridSize);

            while (!p.percolates())
            {
                int x = StdRandom.uniform(gridSize)+1;
                int y = StdRandom.uniform(gridSize)+1;

                if (!p.isOpen(x, y))
                {
                    p.open(x, y);
                    openSites++;
                }
            }
            
            openSiteRatio[i] = openSites / (gridSize * gridSize);
        }
        
    }

    public double mean() {
        return StdStats.mean(openSiteRatio);
    }
    
    public double stddev() {
        return StdStats.stddev(openSiteRatio);
    }
    
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(repetitions));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(repetitions));
    }
    
    public static void main(final String[] args) {
        int N = 20;        // default value for args[0]
        int T = 50;        // default value for args[1]
        
        if (args.length > 0) 
        {
            N = Integer.parseInt(args[0]);
        }

        if (args.length > 1)
        {
            T = Integer.parseInt(args[1]);
        }

        StdOut.printf("Running percolation tests on grid of size %d for %d times.\n", T, N);

        PercolationStats stats = new PercolationStats(N, T); 
        
        StdOut.printf("mean                      = %.16f\n", stats.mean());
        StdOut.printf("stddev                    = %.16f\n", stats.stddev());
        StdOut.printf("95%% confidence interval   = %.16f, %.16f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}
