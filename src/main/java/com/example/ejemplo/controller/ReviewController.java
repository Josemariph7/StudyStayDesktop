package com.example.ejemplo.controller;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de comentarios.
 */
public class ReviewController implements CRUD<Review> {

    private final ReviewDAO reviewDAO = new ReviewDAO();

    /**
     * Obtiene todos los comentarios.
     * @return Lista de todos los comentarios
     */
    @Override
    public List<Review> getAll() {
        return reviewDAO.getAll();
    }

    /**
     * Obtiene un comentario por su identificador.
     * @param id Identificador del comentario
     * @return El comentario encontrado, o null si no se encuentra
     */
    @Override
    public Review getById(Long id) {
        return reviewDAO.getById(id);
    }

    /**
     * Crea un nuevo comentario.
     * @param review El comentario a crear
     * @return true si el comentario se creó exitosamente, false de lo contrario
     */
    @Override
    public boolean create(Review review) {
        return reviewDAO.create(review);
    }

    /**
     * Actualiza un comentario existente.
     * @param review El comentario a actualizar
     * @return true si el comentario se actualizó exitosamente, false de lo contrario
     */
    @Override
    public boolean update(Review review) {
        return reviewDAO.update(review);
    }

    /**
     * Elimina un comentario por su identificador.
     * @param id Identificador del comentario a eliminar
     * @return true si el comentario se eliminó exitosamente, false de lo contrario
     */
    @Override
    public boolean delete(Long id) {
        return reviewDAO.delete(id);
    }
}
