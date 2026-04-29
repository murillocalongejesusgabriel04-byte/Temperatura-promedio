package Controladores;
import java.util.List;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import Modelos.Temperatura;
import Servicios.TemperaturaServicio;

import java.awt.BorderLayout;

import java.time.LocalDate;
import java.util.Map;



public class TemperaturaControlador {

    public static void grafica(JPanel pnlgrafica, List<Temperatura> datos, String ciudad, LocalDate desde, LocalDate hasta){

        var datosFiltrados = TemperaturaServicio.filtrarPorFecha(datos, ciudad, desde, hasta);

        Map<String, Double> promedios = TemperaturaServicio.getPormedioCiudad(datosFiltrados);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : promedios.entrySet()) {
            dataset.addValue(entry.getValue(), "Temperatura promedio", entry.getKey());
        }

        JFreeChart graficador = ChartFactory.createBarChart(
            "Temperatura promedio por ciudad",
            "Ciudad",
            "Temperatura",
            dataset
        );

        ChartPanel pnlGraficador = new ChartPanel(graficador);
        pnlgrafica.removeAll();
        pnlgrafica.setLayout(new BorderLayout());
        pnlgrafica.add(pnlGraficador, BorderLayout.CENTER);
        pnlgrafica.revalidate();
        pnlgrafica.repaint();

    

       
    }

}
