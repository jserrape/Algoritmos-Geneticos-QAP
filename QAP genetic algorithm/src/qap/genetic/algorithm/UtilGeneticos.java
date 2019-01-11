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
public class UtilGeneticos {

    private final int n;
    private final int flujos[][];
    private final int distancias[][];

    private final Configuracion conf;

    public UtilGeneticos(int n, int flujos[][], int distancias[][]) {
        this.n = n;
        this.flujos = flujos;
        this.distancias = distancias;

        conf = new Configuracion();
    }

    public void generarPoblacionAleatoria(ArrayList<Individuo> poblacion, int tamPoblacion) {
        for (int i = 0; i < tamPoblacion; i++) {
            Individuo ind = new Individuo(n);
            ind.calcularFitness(flujos, distancias);
            poblacion.add(ind);
        }
    }

    public Individuo torneoBinario1(ArrayList<Individuo> poblacion) {
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

    public Individuo torneoBinario2(ArrayList<Individuo> poblacion, Individuo padre1) {
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

    public void mutarPoblacion(ArrayList<Individuo> descendencia) {
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

    public void guardarResultado(String directorio, Individuo mejor) {
        String ruta = directorio + "/" + mejor.getFitness() + ".txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(Arrays.toString(mejor.getCromosoma()));
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Estandar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cruce(int[] padre1, int[] padre2, Individuo hijo1, Individuo hijo2) {
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
}
