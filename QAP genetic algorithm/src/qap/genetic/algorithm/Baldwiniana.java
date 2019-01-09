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
public class Baldwiniana {

    private final int n;
    private final int flujos[][];
    private final int distancias[][];

    private final ArrayList<Individuo> poblacion;
    private ArrayList<Individuo> descendencia;
    private ArrayList<Pair> graficoMejora;

    private final Configuracion conf;
    private final Individuo mejor;

    public Baldwiniana(int n, int f[][], int d[][]) {
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
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < conf.getnIteraciones(); i++) {
            generarPoblacionAleatoria(conf.getTamPoblacion() - 1);
            poblacion.add(mejor);
            descendencia = new ArrayList<>();
            for (int j = 0; j < conf.getTamPoblacion() / 2; j++) {

                Individuo padre1 = torneoBinario1();
                Individuo padre2 = torneoBinario2(padre1);
                Individuo hijo1 = new Individuo(), hijo2 = new Individuo();

                //cruce(padre1.getCromosoma(), padre2.getCromosoma(), hijo1, hijo2);
                
                descendencia.add(hijo1);
                descendencia.add(hijo2);
            }
            mutarPoblacion();
            buscarMejor(i);
            System.gc();
        }
        long endTime = System.currentTimeMillis() - startTime;
        guardarResultado();
        System.out.println("El algoritmo genetico Baldwinin ha tardado " + (endTime / 1000) + " segundos");
    }

    private void guardarResultado() {
        String ruta = "baldwin/" + this.mejor.getFitness() + ".txt";
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
        
            private void buscarMejor(int ite) {
        for (int i = 0; i < conf.getTamPoblacion(); i++) {
            if (descendencia.get(i).getFitness() < mejor.getFitness()) {
                System.out.println("-----Nuevo mejor " + descendencia.get(i).getFitness() + " en la iteracion " + ite);
                mejor.setCromosoma(descendencia.get(i).getCromosoma());
                mejor.calcularFitness(flujos, distancias);
                this.graficoMejora.add(new Pair(ite, mejor.getFitness()));
                if(mejor.getFitness()<45152454){
                    guardarResultado();
                    System.out.println("Oleeeeeeeeeeeeeee");
                }
            }
        }
    }
    
    /**
     * @return the graficoMejora
     */
    public ArrayList<Pair> getGraficoMejora() {
        return graficoMejora;
    }
}
