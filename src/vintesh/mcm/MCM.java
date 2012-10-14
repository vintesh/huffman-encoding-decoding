/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.mcm;

/**
 *
 * @author me12co18
 */
public class MCM {

    protected int[][] m;
    protected int[][] s;

    public void matrixChainOrder(int[] p) {
        int n = p.length - 1;
        m = new int[n][n];
        s = new int[n][n];
        for (int i = 0; i < n; i++) {
            m[i] = new int[n];
            m[i][i] = 0;
            s[i] = new int[n];
        }
        for (int ii = 1; ii < n; ii++) {
            for (int i = 0; i < n - ii; i++) {
                int j = i + ii;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    public void printOptimalParenthesizations() {
        boolean[] inAResult = new boolean[s.length];
        printOptimalParenthesizations(s, 0, s.length - 1, inAResult);
    }

    void printOptimalParenthesizations(int[][] s, int i, int j, /* for pretty printing: */ boolean[] inAResult) {
        if (i != j) {
            printOptimalParenthesizations(s, i, s[i][j], inAResult);
            printOptimalParenthesizations(s, s[i][j] + 1, j, inAResult);
            String istr = inAResult[i] ? "_result " : " ";
            String jstr = inAResult[j] ? "_result " : " ";
            System.out.println(" A_" + i + istr + "* A_" + j + jstr);
            inAResult[i] = true;
            inAResult[j] = true;
        }
    }
}


