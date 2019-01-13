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
public class Individuo {

    private int fitness;
    private int cromosoma[];
    private int mejora[];
    private int tam;

    /**
     * Constructor por defecto
     */
    public Individuo() {
    }

    /**
     * Constructor parametrizado
     *
     * @param t Tamaño del vector solución
     */
    public Individuo(int t) {
        fitness = -1;
        tam = t;
        cromosoma = new int[tam];
        mejora = null;

        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < tam; i++) {
            num.add(i);
        }

        Random rnd = new Random();
        for (int i = 0; i < tam; i++) {
            cromosoma[i] = num.remove(rnd.nextInt((num.size() - 1) + 1));
        }
    }

    /**
     * Constructor parametrizado
     *
     * @param t Tamaño del vector solución
     * @param crom Vector solucion
     * @param f Fitness del individuo
     */
    public Individuo(int t, int crom[], int f) {
        this.tam = t;
        this.cromosoma = crom;
        this.fitness = f;
        mejora = null;
    }

    /**
     * Comprueba si dos individuos son iduales
     *
     * @param indi Individuo con el que comparar
     * @return Booleano que indica si son iguales o no
     */
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

    /**
     * Optimiza con búsqueda local el individuo
     *
     * @param flujos Matriz de flujos
     * @param distancias Matriz de distancias
     * @param lewin Indica si se trata de un algoritmo genetico Lewiniano
     */
    public void aplicarBusquedaLocal(int flujos[][], int distancias[][], boolean lewin) {
        if (mejora == null) {
            mejora = busquedaLocal(flujos, distancias, lewin);
        }
        calcularFitnessLocal(flujos, distancias);
    }

    /**
     * Calcula el fitness del individuo usando el vector mejora
     *
     * @param flujos Matriz de flujos
     * @param distancias Matriz de distancias
     */
    public void calcularFitnessLocal(int flujos[][], int distancias[][]) {
        this.fitness = 0;
        for (int i = 0; i < mejora.length; i++) {
            for (int j = 0; j < mejora.length; j++) {
                int v1 = flujos[i][j];
                int v2 = mejora[i];
                int v3 = mejora[j];
                int v4 = distancias[v2][v3];
                this.fitness += v1 * v4;
            }
        }
        if (this.fitness < 44095032) {
            System.out.println("Fitness error" + this.getFitness());
        }
    }

    /**
     * Calcula el fitness del individuo
     *
     * @param flujos Matriz de flujos
     * @param distancias Matriz de distancias
     */
    public void calcularFitness(int flujos[][], int distancias[][]) {
        this.fitness = 0;
        for (int i = 0; i < this.getCromosoma().length; i++) {
            for (int j = 0; j < this.getCromosoma().length; j++) {
                //System.out.println(i+"\t"+j);
                int v1 = flujos[i][j];
                int v2 = this.getCromosoma()[i];
                int v3 = this.getCromosoma()[j];
                int v4 = distancias[v2][v3];
                this.fitness += v1 * v4;
            }
        }
        if (this.fitness < 44095032) {
            System.out.println("Fitness " + this.getFitness());
        }
    }

    /**
     * Optimiza con búsqueda local el individuo
     *
     * @param flujos Matriz de flujos
     * @param distancias Matriz de distancias
     * @param lewin Indica si se trata de un algoritmo genetico Lewiniano
     * @return Indica si ha mejorado
     */
    public int[] busquedaLocal(int flujos[][], int distancias[][], boolean lewin) {
        Individuo local = new Individuo(this.tam, this.cromosoma, this.fitness);
        Individuo indiAux;
        int vAux;
        boolean mejorado = false;
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                indiAux = new Individuo(local.tam, local.cromosoma, local.fitness);
                vAux = indiAux.getCromosoma()[i];
                indiAux.getCromosoma()[i] = indiAux.getCromosoma()[j];
                indiAux.getCromosoma()[j] = vAux;
                indiAux.calcularFitness(flujos, distancias);
                if (indiAux.getFitness() < local.getFitness()) {
                    local = new Individuo(indiAux.tam, indiAux.cromosoma, indiAux.fitness);
                    mejorado = true;
                }
            }
        }
        if (mejorado && lewin) {
            this.setCromosoma(local.cromosoma);
            this.setFitness(local.fitness);
        }
        return local.getCromosoma();
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
