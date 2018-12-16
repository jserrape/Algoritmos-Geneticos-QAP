/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author juanca
 */
public class Individuo {

    private int fitness;
    private int cromosoma[];
    private int tam;

    public Individuo() {
    }
    
    public Individuo(int t) {
        fitness = -1;
        tam = t;
        cromosoma = new int[tam];

        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            num.add(i);
        }

        Random rnd = new Random();
        for (int i = 0; i < tam; i++) {
            cromosoma[i] = num.remove(rnd.nextInt((num.size() - 1) + 1));
        }
    }

    public boolean igualIndividuo(Individuo indi) {

        if (this.tam != indi.getTam()) {
            return false;
        }

        for (int i = 0; i < this.tam; i++) {
            if (this.cromosoma[i] != indi.getCromosoma()[i]) {
                return false;
            }
        }

        return true;
    }

    public void calcularFitness(int flujos[][], int distancias[][]) {
        this.fitness = 0;
        for (int i = 0; i < this.getCromosoma().length; i++) {
            for (int j = 0; j < this.getCromosoma().length; j++) {
                this.fitness += flujos[i][j] * distancias[this.getCromosoma()[i]][this.getCromosoma()[j]];
            }
        }
        if (this.fitness < 44095032) {
            System.out.println("Fitness "+this.getFitness());
        }
    }

    /**
     * @return the fitness
     */
    public int getFitness() {
        return fitness;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the cromosoma
     */
    public int[] getCromosoma() {
        return cromosoma;
    }

    /**
     * @param cromosoma the cromosoma to set
     */
    public void setCromosoma(int[] cromosoma) {
        this.cromosoma = cromosoma;
    }

    /**
     * @return the tam
     */
    public int getTam() {
        return tam;
    }

    /**
     * @param tam the tam to set
     */
    public void setTam(int tam) {
        this.tam = tam;
    }

}
