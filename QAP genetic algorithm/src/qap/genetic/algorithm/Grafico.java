/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qap.genetic.algorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author juanca
 */
public class Grafico extends JFrame {

    private JPanel panel;
    private final ArrayList<Pair> datos;

    public Grafico(ArrayList<Pair> dat) {
        setTitle("Evolución del mejor fitness");
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        this.datos=dat;
        init();
    }

    private void init() {
        panel = new JPanel();
        getContentPane().add(panel);
        // Fuente de Datos
        DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
        for(int i=1;i<datos.size();i++){
            line_chart_dataset.addValue(datos.get(i).getFitness(), "Fitness", Integer.toString(datos.get(i).getIteracion()));
        }

        // Creando el Grafico
        JFreeChart chart = ChartFactory.createLineChart("Evolución del fitness", "Iteracion", "Fitness", line_chart_dataset, PlotOrientation.VERTICAL, true, true, false);

        // Mostrar Grafico
        ChartPanel chartPanel = new ChartPanel(chart);
        panel.add(chartPanel);
        
        try {
            ChartUtilities.saveChartAsJPEG(new File("grafico.jpg"), chart, 2000, 500);
        } catch (IOException ex) {
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
