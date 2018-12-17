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

    private final int tamPoblacion = 500;
    private final int nIteraciones = 25000;
    private final int probMutacion = 80;
    private final String fichero ="qap.datos/chr12a.dat";

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
    
    
}
