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

    private final int n;
    private final int flujos[][];
    private final int distancias[][];

    private final ArrayList<Individuo> poblacion;
    private ArrayList<Individuo> descendencia;

    private final Configuracion conf;

    private final Individuo mejor;

    public Estandar(int n, int f[][], int d[][]) {
        this.n = n;
        this.flujos = f;
        this.distancias = d;
        poblacion = new ArrayList<>();
        conf = new Configuracion();

        mejor = new Individuo(n);
        mejor.calcularFitness(flujos, distancias);
    }

    public void ejecutar() {
        for (int i = 0; i < conf.getnIteraciones(); i++) {
            generarPoblacionAleatoria(conf.getTamPoblacion() - 1);
            poblacion.add(mejor);
            descendencia = new ArrayList<>();
            for (int j = 0; j < conf.getTamPoblacion() / 2; j++) {

                Individuo padre1 = torneoBinario1();
                Individuo padre2 = torneoBinario2(padre1);
                Individuo hijo1 = new Individuo(), hijo2 = new Individuo();

                cruce(padre1.getCromosoma(), padre2.getCromosoma(), hijo1, hijo2);
                descendencia.add(hijo1);
                descendencia.add(hijo2);
            }
            mutarPoblacion();
            buscarMejor();
        }
    }

    private void buscarMejor() {
        for (int i = 0; i < conf.getTamPoblacion(); i++) {
            if (descendencia.get(i).getFitness() < mejor.getFitness()) {
                System.out.println("-----Nuevo mejor " + descendencia.get(i).getFitness());
                mejor.setCromosoma(descendencia.get(i).getCromosoma());
                mejor.calcularFitness(flujos, distancias);
            }
        }
    }

    private void mutarPoblacion() {
        int prob;
        int n1, n2, aux;
        for (int i = 0; i < conf.getTamPoblacion(); i++) {
            prob = (int) (Math.random() * 100) + 1;
            if (conf.getProbMutacion() > prob) {
                n1 = (int) (Math.random() * n);
                n2 = (int) (Math.random() * n);
                while (n1 == n2) {
                    n2 = (int) (Math.random() * n);
                }
                aux = descendencia.get(i).getCromosoma()[n1];
                int[] cromAux = descendencia.get(i).getCromosoma().clone();
                cromAux[n1] = cromAux[n2];
                cromAux[n2] = aux;
                descendencia.get(i).setCromosoma(cromAux);
            }
            descendencia.get(i).calcularFitness(flujos, distancias);
        }
    }

    private void cruce(int[] padre1, int[] padre2, Individuo hijo1, Individuo hijo2) {
        int corte = (int) (Math.random() * n);
        int cromosoma1[], cromosoma2[];
        cromosoma1 = new int[n];
        cromosoma2 = new int[n];
        for (int i = 0; i < n; i++) {
            cromosoma1[i] = cromosoma2[i] = -1;
        }
        //Relleno el hijo 1
        System.arraycopy(padre1, 0, cromosoma1, 0, corte);
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
                    --coincidencias;
                    break;
                }
            }
        }
        hijo1.setCromosoma(cromosoma1);

        //Relleno el hijo 2
        System.arraycopy(padre2, 0, cromosoma2, 0, corte);
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
                    --coincidencias;
                    break;
                }
            }
        }
        hijo2.setCromosoma(cromosoma2);

        for (int i = 0; i < n; i++) {
            if (cromosoma1[i] == -1) {
                System.out.println("Padre 1: " + Arrays.toString(padre1));
                System.out.println("Padre 2: " + Arrays.toString(padre2));
                System.out.println(Arrays.toString(cromosoma1));
            }
            if (cromosoma2[i] == -1) {
                System.out.println("Padre 1: " + Arrays.toString(padre1));
                System.out.println("Padre 2: " + Arrays.toString(padre2));
                System.out.println(Arrays.toString(cromosoma2));
            }
        }
    }

    private boolean yaEsta(int cromosoma[], int pos, int num) {
        for (int i = 0; i < pos; i++) {
            if (cromosoma[i] == num) {
                return true;
            }
        }
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
