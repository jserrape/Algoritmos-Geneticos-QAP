/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
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
        leerFichero();
        //Estandar
        Estandar est = new Estandar(n, flujos, distancias);
        est.ejecutar();
        new Grafico(est.getGraficoMejora(),"estandar").setVisible(true);

        //Baldwin
        Baldwiniana bald = new Baldwiniana(n, flujos, distancias);
        bald.ejecutar();
        new Grafico(bald.getGraficoMejora(),"Balwiniana").setVisible(true);
        
        //Lamarck
        Lamarckiana lam = new Lamarckiana(n, flujos, distancias);
        lam.ejecutar();
        new Grafico(lam.getGraficoMejora(),"Lamarckiana").setVisible(true);
    }

    private static void leerFichero() throws FileNotFoundException {
        Configuracion conf = new Configuracion();
        try (Scanner sc = new Scanner(new File(conf.getFichero()))) {
            if (sc.hasNextInt()) {
                n = sc.nextInt();
                flujos = new int[n][n];
                distancias = new int[n][n];
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    flujos[i][j] = sc.nextInt();
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    distancias[i][j] = sc.nextInt();
                }
            }
        }
    }

    private static void imprimirFichero() {
        System.out.println("Tamaño del problema " + n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(flujos[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(distancias[i][j] + "\t");
            }
            System.out.println("");
        }
    }
}
