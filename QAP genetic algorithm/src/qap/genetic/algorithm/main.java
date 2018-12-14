/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author juanca
 */
public class main {

    private static int n;
    private static int flujos[][];
    private static int distancias[][];

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Comienza...");
        try (Scanner sc = new Scanner(new File("qap.datos/chr12c.dat"))) {
            if (sc.hasNextInt()) {
                n = sc.nextInt();
                System.out.println("Tamaño del problema " + n);

                flujos = new int[n][n];
                distancias = new int[n][n];
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    flujos[i][j] = sc.nextInt();
                    System.out.print(flujos[i][j] + "\t");
                }
                System.out.println("");
            }
            System.out.println("");
            System.out.println("");
            System.out.println("");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distancias[i][j] = sc.nextInt();
                    System.out.print(distancias[i][j] + "\t");
                }
                System.out.println("");
            }
        }
    }

}
