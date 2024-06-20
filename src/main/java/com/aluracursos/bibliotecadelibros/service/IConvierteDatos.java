package com.aluracursos.bibliotecadelibros.service;

public interface IConvierteDatos
{
    <T> T obtenerDatos(String json, Class<T> clase);
}
