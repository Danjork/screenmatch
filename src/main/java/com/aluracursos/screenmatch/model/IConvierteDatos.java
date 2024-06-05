package com.aluracursos.screenmatch.model;

public interface IConvierteDatos {
    //T tipo de datos genericos
    <T> T obtenerDatos(String json, Class<T> clase);
}
