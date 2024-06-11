/*
 * StudyStay © 2024
 *
 * All rights reserved.
 *
 * This software and associated documentation files (the "Software") are owned by StudyStay. Unauthorized copying, distribution, or modification of this Software is strictly prohibited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this Software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * StudyStay
 * José María Pozo Hidalgo
 * Email: josemariph7@gmail.com
 *
 *
 */

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
