/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private ArrayList<Pair> graficoMejora;

    private final Configuracion conf;

    private final Individuo mejor;

    public Estandar(int n, int f[][], int d[][]) {
        this.n = n;
        this.flujos = f;
        this.distancias = d;
        poblacion = new ArrayList<>();
        graficoMejora = new ArrayList<>();
        conf = new Configuracion();

        mejor = new Individuo(n);
        mejor.calcularFitness(flujos, distancias);
        this.graficoMejora.add(new Pair(1, mejor.getFitness()));
    }

    public void ejecutar() {
        UtilGeneticos util = new UtilGeneticos(n, this.flujos, this.distancias);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < conf.getnIteraciones(); i++) {
            util.generarPoblacionAleatoria(poblacion, conf.getTamPoblacion() - 1);
            poblacion.add(mejor);
            descendencia = new ArrayList<>();
            for (int j = 0; j < conf.getTamPoblacion() / 2; j++) {

                Individuo padre1 = util.torneoBinario1(poblacion);
                Individuo padre2 = util.torneoBinario2(poblacion, padre1);
                Individuo hijo1 = new Individuo(), hijo2 = new Individuo();

                cruce(padre1.getCromosoma(), padre2.getCromosoma(), hijo1, hijo2);
                descendencia.add(hijo1);
                descendencia.add(hijo2);
            }
            util.mutarPoblacion(descendencia);
            buscarMejor(i, util);
        }
        util.guardarResultado("estandar", mejor);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("El algoritmo genetico estandar ha tardado " + (endTime / 1000) + " segundos.");
    }

    private void buscarMejor(int ite, UtilGeneticos util) {
        for (int i = 0; i < conf.getTamPoblacion(); i++) {
            if (descendencia.get(i).getFitness() < mejor.getFitness()) {
                System.out.println("-----Nuevo mejor " + descendencia.get(i).getFitness() + " en la iteracion " + ite);
                mejor.setCromosoma(descendencia.get(i).getCromosoma());
                mejor.calcularFitness(flujos, distancias);
                this.graficoMejora.add(new Pair(ite, mejor.getFitness()));
                if (mejor.getFitness() < 45152454) {
                    util.guardarResultado("estandar", mejor);
                    System.out.println("Oleeeeeeeeeeeeeee");
                }
            }
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
    }

    private boolean yaEsta(int cromosoma[], int pos, int num) {
        for (int i = 0; i < pos; i++) {
            if (cromosoma[i] == num) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the graficoMejora
     */
    public ArrayList<Pair> getGraficoMejora() {
        return graficoMejora;
    }
}
