/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vintesh.mcm;

import java.util.Arrays;

/**
 *
 * @author me12co18
 */
public class Main {

    public static void main(String[] args) {

        MCM mcm = new MCM();
        int matrixDimentions[] = {30, 35, 15, 5, 10, 20, 25};
        mcm.matrixChainOrder(matrixDimentions);

        for (int i = 0; i < mcm.m.length; i++) {
            System.out.println(Arrays.toString(mcm.m[i]));
        }

        mcm.printOptimalParenthesizations();
    }
}
