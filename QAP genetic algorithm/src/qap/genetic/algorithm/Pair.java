/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

/**
 *
 * @author juanca
 */
public class Pair {

    private int iteracion;
    private int fitness;

    /**
     * Constructor por defecto
     */
    public Pair() {
    }

    /**
     * Constructor parametrizado
     * @param ite Numero de iteracion
     * @param fit Firness asociado a la iteracion
     */
    public Pair(int ite, int fit) {
        this.iteracion = ite;
        this.fitness = fit;
    }

    /**
     * @return the iteracion
     */
    public int getIteracion() {
        return iteracion;
    }

    /**
     * @param iteracion the iteracion to set
     */
    public void setIteracion(int iteracion) {
        this.iteracion = iteracion;
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
}
