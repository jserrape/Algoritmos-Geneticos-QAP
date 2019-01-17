/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.util.ArrayList;

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
    
    private final ArrayList<Pair> graficoMejora;
    
    private final Configuracion conf;
    
    private final Individuo mejor;
    
    private int sinMejorar;
    private boolean haMejorado;

    /**
     * Constructor parametrizado
     *
     * @param n Tamaño de la matriz
     * @param f Matriz de flujos
     * @param d Matriz de distancias
     */
    public Estandar(int n, int f[][], int d[][]) {
        this.n = n;
        this.flujos = f;
        this.distancias = d;
        poblacion = new ArrayList<>();
        graficoMejora = new ArrayList<>();
        conf = new Configuracion();
        
        this.sinMejorar = 999999;
        this.haMejorado = false;
        
        mejor = new Individuo(n);
        mejor.calcularFitness(flujos, distancias);
        this.graficoMejora.add(new Pair(1, mejor.getFitness()));
    }

    /**
     * Función principal del algoritmo
     */
    public void ejecutar() {
        UtilGeneticos util = new UtilGeneticos(n, this.flujos, this.distancias);
        descendencia = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < conf.getnIteraciones(); i++) {
            if (i % 1000 == 0) {
                System.out.println("It " + i);
            }
            if ((this.sinMejorar >= this.conf.getItSinMejora()) || util.reiniciarPorVariedad(poblacion)) {
                util.generarPoblacionAleatoria(poblacion, conf.getTamPoblacion() - 1);
                poblacion.add(mejor);
                this.sinMejorar = 0;
            }
            descendencia.clear();
            for (int j = 0; j < conf.getTamPoblacion() / 2; j++) {
                
                Individuo padre1 = util.torneoBinario1(poblacion);
                Individuo padre2 = util.torneoBinario2(poblacion, padre1);
                Individuo hijo1 = new Individuo(), hijo2 = new Individuo();
                
                util.cruce(padre1.getCromosoma(), padre2.getCromosoma(), hijo1, hijo2);
                
                descendencia.add(hijo1);
                descendencia.add(hijo2);
            }
            
            util.mutarPoblacion(descendencia);
            buscarMejor(i, util);
            poblacion.clear();
            for (int j = 0; j < descendencia.size() - 1; j++) {
                poblacion.add(descendencia.get(j));
            }
            poblacion.add(mejor);
        }
        util.guardarResultado("estandar", mejor);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("El algoritmo genetico estandar ha tardado " + (endTime / 1000) + " segundos.");
    }

    /**
     * Buscar el mejor individuo de la población y comprueba si es mejor que el
     * anterior
     *
     * @param ite Número de la iteración actual
     * @param util Clase con las funciones comunes
     */
    private void buscarMejor(int ite, UtilGeneticos util) {
        for (int i = 0; i < conf.getTamPoblacion(); i++) {
            if (descendencia.get(i).getFitness() < mejor.getFitness()) {
                System.out.println("Nuevo mejor " + descendencia.get(i).getFitness() + " en la iteracion " + ite);
                this.haMejorado = true;
                mejor.setCromosoma(descendencia.get(i).getCromosoma().clone());
                mejor.calcularFitness(flujos, distancias);
                this.graficoMejora.add(new Pair(ite, mejor.getFitness()));
                util.guardarResultado("estandar", mejor);
            }
        }
        if (this.haMejorado) {
            this.haMejorado = false;
            this.sinMejorar = 0;
        } else {
            ++this.sinMejorar;
        }
    }

    /**
     * @return the graficoMejora
     */
    public ArrayList<Pair> getGraficoMejora() {
        return graficoMejora;
    }
}
