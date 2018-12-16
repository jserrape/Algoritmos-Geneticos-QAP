/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author juanca
 */
public class Estandar {

    private int n;
    private int flujos[][];
    private int distancias[][];

    private ArrayList<Individuo> poblacion, descendencia;

    private Configuracion conf;

    int tamPoblacion = 50;
    int tabu;
    int costes[], costesAux[];
    double probGen;
    int nGeneracion;
    int anteriorMejor;

    public Estandar(int n, int f[][], int d[][]) {
        this.n = n;
        this.flujos = f;
        this.distancias = d;
        poblacion = new ArrayList<>();
        conf = new Configuracion();

        Individuo padre1 = new Individuo(6);
        Individuo padre2 = new Individuo(6);
        cruce(/*padre1.getCromosoma(), padre2.getCromosoma()*/);
    }

    public void ejecutar() {
        
    }

    private void cruce(/*int[] padre1, int[] padre2*/) {
        int[] padre1 = new int[]{2, 5, 0, 1, 4, 3};
        int[] padre2 = new int[]{1, 2, 0, 5, 3, 4};
        System.out.println("Padre 1 " + Arrays.toString(padre1));
        System.out.println("Padre 2 " + Arrays.toString(padre2));
        int n = 6; //Borrar
        int corte = (int) (Math.random() * n);
        corte = 2; //Borrar
        int cromosoma1[], cromosoma2[];
        cromosoma1 = new int[n];
        cromosoma2 = new int[n];
        System.out.println("Corte " + corte);
        //Relleno el hijo 1
        for (int i = 0; i < corte; i++) {
            cromosoma1[i] = padre1[i];
        }
        int coincidencias = 0;
        for (int i = corte; i < n; i++) {
            if (!yaEsta(cromosoma1, corte + 1, padre2[i])) {
                cromosoma1[i - coincidencias] = padre2[i];
            } else {
                ++coincidencias;
            }
        }
        for (int i = n - coincidencias; i < n; i++) {
            for (int j = corte; j < n; j++) {
                if (!yaEsta(cromosoma1, n - coincidencias, padre1[j])) {
                    cromosoma1[i] = padre1[j];
                    break;
                }
            }
        }
        System.out.println("Hijo 1" + Arrays.toString(cromosoma1));
        //Relleno el hijo 2
        for (int i = 0; i < corte; i++) {
            cromosoma2[i] = padre2[i];
        }
        coincidencias = 0;
        for (int i = corte; i < n; i++) {
            if (!yaEsta(cromosoma2, corte + 1, padre1[i])) {
                cromosoma2[i - coincidencias] = padre1[i];
            } else {
                ++coincidencias;
            }
        }
        for (int i = n - coincidencias; i < n; i++) {
            for (int j = corte; j < n; j++) {
                if (!yaEsta(cromosoma2, n - coincidencias, padre2[j])) {
                    cromosoma2[i] = padre2[j];
                    break;
                }
            }
        }
        System.out.println("Hijo 2" + Arrays.toString(cromosoma2));
    }

    private boolean yaEsta(int cromosoma[], int pos, int num) {
        for (int i = 0; i < pos; i++) {
            if (cromosoma[i] == num) {
                //System.out.println("Ya esta el " + num + " " + Arrays.toString(cromosoma) + " en la pos " + i);
                return true;
            }
        }
        //System.out.println("No esta el " + num);
        return false;
    }

    private void generarPoblacionAleatoria(int tamPoblacion) {
        for (int i = 0; i < tamPoblacion; i++) {
            Individuo ind = new Individuo(n);
            ind.calcularFitness(flujos, distancias);
            poblacion.add(ind);
        }
    }

    private Individuo torneoBinario1() {
        Random rnd = new Random();

        int p1 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
        int p2 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
        if (p1 == p2) {
            while (p1 == p2) {
                p2 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
            }
        }
        Individuo ind1 = poblacion.get(p1);
        Individuo ind2 = poblacion.get(p2);

        if (ind1.getFitness() < ind2.getFitness()) {
            return ind1;
        } else {
            return ind2;
        }
    }

    private Individuo torneoBinario2(Individuo padre1) {
        Random rnd = new Random();

        int p1 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
        int p2 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
        if (p1 == p2 || poblacion.get(p1).igualIndividuo(padre1) || poblacion.get(p2).igualIndividuo(padre1)) {
            while (p1 == p2 || poblacion.get(p1).igualIndividuo(padre1) || poblacion.get(p2).igualIndividuo(padre1)) {
                p1 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
                p2 = rnd.nextInt((conf.getTamPoblacion() - 1) + 1);
            }
        }
        Individuo ind1 = poblacion.get(p1);
        Individuo ind2 = poblacion.get(p2);

        if (ind1.getFitness() < ind2.getFitness()) {
            return ind1;
        } else {
            return ind2;
        }
    }

}
