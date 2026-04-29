package Servicios;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Modelos.Temperatura;

public class TemperaturaServicio {

    public static List<Temperatura> getDatos(String nombreArchivo){
      DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      try {
          var lineas = Files.lines(Paths.get(nombreArchivo));

          return lineas.skip(1)
                .map(linea -> linea.split(","))
                .map(textos -> new Temperatura(textos[0], LocalDate.parse(textos[1], formatoFecha), Double.parseDouble(textos[2])))
                .collect(Collectors.toList());

          
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }


    
    public static List<Temperatura> filtrarPorFecha(List<Temperatura> datos, String ciudades, LocalDate desde, LocalDate hasta){
      return datos.stream()
       .filter(t -> (ciudades == null || t.getCiudad().equals(ciudades)) && 
          !t.getFecha().isBefore(desde) && 
          !t.getFecha().isAfter(hasta))
       .collect(Collectors.toList());
    }



    public static Map<String, Double> getPormedioCiudad(List<Temperatura> datos) {
        return datos.stream()
                .collect(Collectors.groupingBy(Temperatura::getCiudad, Collectors.averagingDouble(Temperatura::getTemperatura)));
    }



    public static Temperatura getCiudadMasCalurosa(List<Temperatura> datos, LocalDate fecha) {
        return datos.stream()
                .filter(t -> t.getFecha().equals(fecha))
                .max(Comparator.comparingDouble(Temperatura::getTemperatura))
                .orElse(null);

    }

    public static Temperatura getCiudadMenosCalurosa(List<Temperatura> datos, LocalDate fecha) {
        return datos.stream()
                .filter(t -> t.getFecha().equals(fecha))
                .min(Comparator.comparingDouble(Temperatura::getTemperatura))
                .orElse(null);
    }
 


   

   

    

}
