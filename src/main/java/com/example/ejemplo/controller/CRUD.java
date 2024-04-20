package com.example.ejemplo.controller;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD.
 * @param <E> Tipo de entidad.
 */
public interface CRUD<E> {

    /**
     * Obtiene todos los elementos de la entidad.
     * @return Una lista de todos los elementos de la entidad.
     */
    List<E> getAll();

    /**
     * Obtiene un elemento de la entidad por su ID.
     * @param id El ID del elemento a obtener.
     * @return El elemento correspondiente al ID especificado.
     */
    E getById(Long id);

    /**
     * Crea un nuevo elemento en la entidad.
     * @param entity El elemento a crear.
     * @return true si se creó correctamente, false de lo contrario.
     */
    boolean create(E entity);

    /**
     * Actualiza un elemento existente en la entidad.
     * @param entity El elemento a actualizar.
     * @return true si se actualizó correctamente, false de lo contrario.
     */
    boolean update(E entity);

    /**
     * Elimina un elemento de la entidad por su ID.
     * @param id El ID del elemento a eliminar.
     * @return true si se eliminó correctamente, false de lo contrario.
     */
    boolean delete(Long id);
}
