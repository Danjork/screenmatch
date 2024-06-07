package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import javax.crypto.ExemptionMechanismSpi;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String  URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=219096d3";

    private ConvierteDatos conversor = new ConvierteDatos();



    public void muestraMenu(){

        System.out.println("Ingrese el nombre de la serie que desee buscar");
        var nombreSerie = teclado.nextLine();
        var json =  consumoApi.obtenerDatos(URL_BASE+ nombreSerie.replace(" ","+") + API_KEY);
        var datos = conversor.obtenerDatos(json , DatosSerie.class);
        System.out.println(json);

        //Buscao todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
		//for se hace para recorrer la cantidad de temporadas
		for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
			json = consumoApi.obtenerDatos(URL_BASE+ nombreSerie.replace(" ","+") + "&Season="+ i + API_KEY);
			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporadas.class);
			temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de los episodios

//        for (int i = 0 ; i < datos.totalDeTemporadas() ; i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }
//Mostrar solo el titulo de los episodios forma corta usando lamda
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DatosEpisodio> datosEpisodios =  temporadas.stream()
                //por cada temporada = t van a tener episodios
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //Top 5
//        System.out.println("Top 5 Episodios");
//
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro N/A" + e))
//                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//                .peek(e -> System.out.println("Primer ordenar (M>m) " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("tercer filtro (m>M)" + e))
//                .limit(5)//top 5
//
//                .forEach(System.out::println);

        System.out.println("Episodios temporadas");
        //convertir los datos a una lista episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d)))
                .collect(Collectors.toList());
      //  episodios.forEach(System.out::println);

        //System.out.println("Fin episodios temporadas");



      //  System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha,1,1);


//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda) )
//        .forEach(e-> System.out.println(
//                "Temporada " + e.getTemporada() +
//                        " Episodio " + e.getTitulo() +
//                        " Fecha de Lanzamiento " + e.getFechaDeLanzamiento().format(dtf)
//
//        ));


        System.out.println("Escribe el titulo de deseas ver");
        var pedazoTitulo = teclado.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))//buscar el resultado
                .findFirst();//buscar
        if(episodioBuscado.isPresent()){
            System.out.println(" Episodio encontrado");
            System.out.println("Los datos son: " + episodioBuscado.get());
        }else {
            System.out.println("Episodio no encontrado");
        }

    }

}
