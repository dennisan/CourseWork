/**----------------------------------------------------------------
 *  Author:        Dennis Angeline
 *  Written:       9/19/14
 *  Last updated:  9/19/14
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *  
 *  Programming Assignment 1 for Algs4
 *
 *----------------------------------------------------------------**/

public class Percolation 
{
    private int size;                     // the specified grid size
    private boolean[][] cells;            // the current open/close state of each cell 
    private WeightedQuickUnionUF uf;      // the WeightedQuickUnionUF implementation 
    private int upperVSite;
    private int lowerVSite;
    private boolean drawInit = false;
    
    public Percolation(int N) 
    {    
        /**------------------------------------------
         * create N-by-N grid, with all sites blocked
         *-----------------------------------------**/
        if (N <= 0)
            throw new IllegalArgumentException ();

        size = N;
        cells = new boolean[size][size];
       
        // mark all cells as blocked (false) 
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                cells[i][j] = false;   
            }
        }

        // create the union find graph with 2 additional virtual nodes
        uf = new WeightedQuickUnionUF(size*size + 2);

        // find the id of the virtual nodes
        upperVSite = 0;
        lowerVSite = size*size + 1;
        
        // union the virtual nodes to top and bottom row
        for (int i = 1; i <= N; i++) 
        {
            uf.union(upperVSite, i);
            uf.union(lowerVSite, lowerVSite-i);
            
        }
    }
    
    private void validateArgs(int i, int j)
    {
        if (i < 1 || i > size)
            throw new IndexOutOfBoundsException("Index i is out of range");

        if (j < 1 || j > size)
            throw new IndexOutOfBoundsException("Index j is out of range");
        
        return;
    }
    
    private void showGrid() 
    {
        /**------------------------------------------
         * Displays a visualization of the Grid
         *-----------------------------------------**/
        int blockSize = 10;
        
        if (!drawInit) 
        {
            int canvasSize = (size-1) * blockSize;
            StdDraw.setCanvasSize(canvasSize, canvasSize);
            StdDraw.setYscale(canvasSize, 0);
            StdDraw.setXscale(0, canvasSize);
            StdDraw.clear(StdDraw.BLACK);
            drawInit = true;
        }

        for (int row = 0; row < size; row++)
        {
            for (int col = 0; col < size; col++)
            {
                if (isOpen(row+1, col+1))
                {
                    if (isFull(row+1, col+1))
                        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    else
                        StdDraw.setPenColor(StdDraw.WHITE);

                    StdDraw.filledSquare(col*blockSize, row*blockSize, blockSize/2.0);
                }
            }
        }
   }    
    
    private int xyToIndex(int row, int col) 
    {
        if (row < 1 || row > size)
            throw new IndexOutOfBoundsException("Index i is out of range - " + row);

        if (col < 1 || col > size)
            throw new IndexOutOfBoundsException("Index j is out of range - " + col);

        return (row - 1) * size + col;     
    }
    
    public void open(int row, int col) 
    {
        validateArgs(row, col);

        cells[row - 1][col - 1] = true;
        
        int siteIndex = xyToIndex(row, col);

        // union node with node to left
        if (col > 1 && isOpen(row, col-1)) 
            uf.union(siteIndex, xyToIndex(row, col-1));

        // union node with node to right
        if (col < size && isOpen(row, col+1)) 
            uf.union(siteIndex, xyToIndex(row, col+1));
        
        // union node with node above
        if (row > 1 && isOpen(row-1, col)) 
            uf.union(siteIndex, xyToIndex(row-1, col));

        // union node with node below
        if (row < size && isOpen(row+1, col)) 
            uf.union(siteIndex, xyToIndex(row+1, col));
    }
   
    public boolean isOpen(int row, int col) 
    {
        validateArgs(row, col);

        return cells[row-1][col-1]; 
    }
   
    public boolean isFull(int row, int col) 
    {
        validateArgs(row, col);

        if (!isOpen(row, col))
            return false;
        
        int nodeId = xyToIndex(row, col);
        
        return uf.connected(upperVSite, nodeId); 
    }
   
    public boolean percolates() 
    {
       return uf.connected(upperVSite, lowerVSite);
    }
   
    public static void main(String[] args) 
    {
        int size = 30;
        int openSites = 0;
        Percolation p = new Percolation(size);
   
        while (!p.percolates())
        {
            int x = StdRandom.uniform(size)+1;
            int y = StdRandom.uniform(size)+1;

            if (!p.isOpen(x, y))
            {
                p.open(x, y);
                openSites++;
            }

            p.showGrid();
        }
        
        StdOut.println("System percolates with " + openSites + " open sites.");
    }
}
