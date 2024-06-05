package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosSerie(
       @JsonAlias("title") String titulo,
       @JsonAlias("totalSeasons") Integer totalDeTemporadas,
        @JsonAlias("imdbRating")  String evaluacion
) {
}
