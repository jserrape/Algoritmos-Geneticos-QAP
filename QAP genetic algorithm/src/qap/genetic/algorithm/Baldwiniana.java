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

    /**
     * @return the graficoMejora
     */
    public ArrayList<Pair> getGraficoMejora() {
        return graficoMejora;
    }
}
