package com.aluracursos.bibliotecadelibros.principal;

import com.aluracursos.bibliotecadelibros.model.Datos;
import com.aluracursos.bibliotecadelibros.model.DatosLibros;
import com.aluracursos.bibliotecadelibros.service.ConsumirApi;
import com.aluracursos.bibliotecadelibros.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal
{
    private static final String URL_BASE = "https://gutendex.com/books/";

    private final ConvierteDatos conversor = new ConvierteDatos();
    private ConsumirApi consumirApi = new ConsumirApi();
    private Scanner teclado = new Scanner(System.in);

    public void mostrarMenu()
    {
        var json = new ConsumirApi().obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        //Top 10 de los mejores libros con preloader mientras llega la información
        System.out.println("#****************************#");
        System.out.println("Top 10 de los mejores libros");
        System.out.println("#****************************#");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        json = consumirApi.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("#****************************#");
            System.out.println("Libro Encontrado ");
            System.out.println("#****************************#");
            System.out.println("Titulo: " + libroBuscado.get().titulo());
            System.out.println("Autor: " + libroBuscado.get().autor().get(0).nombre());
        }else {
            System.out.println("Libro no encontrado");
        }

        //Trabajando con estadisticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDeDescargas() >0 )
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("#****************************#");
        System.out.println("Estadisticas de descargas");
        System.out.println("#****************************#");
        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: "+ est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println("Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());
    }

}
