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

    private static int tamPoblacion = 50;
    private static int nIteraciones = 100;

    private static ArrayList<Individuo> poblacion;

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Comienza...");
        leerFichero();
        //imprimirFichero();
        generarPoblacion();
        algoritmoEstandar();
    }

    private static void generarPoblacion() {
        poblacion = new ArrayList<>();
        for (int i = 0; i < tamPoblacion; i++) {
            Individuo ind = new Individuo(n);
            ind.calcularFitness(flujos, distancias);
            poblacion.add(ind);
        }
    }

    private static void algoritmoEstandar() {
        for (int i = 0; i < nIteraciones; i++) {

            //Selecciono tamPoblacion individuos por torneo
            ArrayList<Individuo> poblacionAuxiliar = new ArrayList<>();
            Random rnd = new Random();
            for (int j = 0; j < tamPoblacion; j++) {
                int p1 = rnd.nextInt((tamPoblacion - 1) + 1);
                int p2 = rnd.nextInt((tamPoblacion - 1) + 1);
                if (p1 == p2) {
                    while (p1 == p2) {
                        p2 = rnd.nextInt((tamPoblacion - 1) + 1);
                    }
                }
                Individuo ind1 = poblacion.get(p1);
                Individuo ind2 = poblacion.get(p2);
                if (ind1.getFitness() < ind2.getFitness()) {
                    poblacionAuxiliar.add(ind1);
                } else {
                    poblacionAuxiliar.add(ind2);
                }
            }
        }
    }

    private static void leerFichero() throws FileNotFoundException {
        try (Scanner sc = new Scanner(new File("qap.datos/tai256c.dat"))) {
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
