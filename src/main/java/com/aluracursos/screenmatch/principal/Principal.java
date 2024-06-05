package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporadas;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    }

}