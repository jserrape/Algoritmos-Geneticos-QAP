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
public class Configuracion {

    /**
     * Tamaño de la poblacion
     */
    private final int tamPoblacion = 50;

    /**
     * Numero de iteraciones que va a ejecutar el algoritmo
     */
    private final int nIteraciones = 10000;

    /**
     * Probabilidad de mutar un individuo
     */
    private final int probMutacion = 80;

    /**
     * Iteraciones tras las que se reinicializa el algoritmo si no mejora
     */
    private final int itSinMejora = 300;

    /**
     * Fichero qap a leer
     */
    //private final String fichero ="qap.datos/chr12a.dat";
    private final String fichero = "qap.datos/tai256c.dat"; //Cota --> 44095032

    /**
     * @return the tamPoblacion
     */
    public int getTamPoblacion() {
        return tamPoblacion;
    }

    /**
     * @return the nIteraciones
     */
    public int getnIteraciones() {
        return nIteraciones;
    }

    public int getProbMutacion() {
        return probMutacion;
    }

    public String getFichero() {
        return fichero;
    }

    /**
     * @return the itSinMejora
     */
    public int getItSinMejora() {
        return itSinMejora;
    }

}
