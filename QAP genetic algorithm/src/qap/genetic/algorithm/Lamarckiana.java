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
public class Lamarckiana {

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

    public Lamarckiana(int n, int f[][], int d[][]) {
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

    public void ejecutar() {
        UtilGeneticos util = new UtilGeneticos(n, this.flujos, this.distancias);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < conf.getnIteraciones(); i++) {
            if (this.sinMejorar >= this.conf.getItSinMejora()) {
                System.out.println("Reinicializo");
                util.generarPoblacionAleatoria(poblacion, conf.getTamPoblacion() - 1);
                poblacion.add(mejor);
                this.sinMejorar = 0;
            }
            descendencia = new ArrayList<>();
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
            System.gc();
        }
        util.guardarResultado("lamarck", mejor);
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("El algoritmo genetico de Lamarck ha tardado " + (endTime / 1000) + " segundos.");
    }

    private void buscarMejor(int ite, UtilGeneticos util) {
        for (int i = 0; i < conf.getTamPoblacion(); i++) {
            descendencia.get(i).aplicarBusquedaLocal(this.flujos, this.distancias, true);
            if (descendencia.get(i).getFitness() < mejor.getFitness()) {
                System.out.println("-----Nuevo mejor " + descendencia.get(i).getFitness() + " en la iteracion " + ite);
                this.haMejorado = true;
                mejor.setCromosoma(descendencia.get(i).getCromosoma());
                mejor.calcularFitness(flujos, distancias);
                this.graficoMejora.add(new Pair(ite, mejor.getFitness()));
                //if(mejor.getFitness()<45152454){
                util.guardarResultado("lamarck", mejor);
                //    System.out.println("Oleeeeeeeeeeeeeee");
                //}
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
