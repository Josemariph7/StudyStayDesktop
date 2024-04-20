package com.example.ejemplo.controller;

import com.example.ejemplo.dao.CommentDAO;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de comentarios.
 */
public class CommentController implements CRUD<Comment> {

    private final CommentDAO commentDAO = new CommentDAO();

    /**
     * Obtiene todos los comentarios.
     * @return Una lista de todos los comentarios.
     */
    @Override
    public List<Comment> getAll() {
        return commentDAO.getAll();
    }

    /**
     * Obtiene un comentario por su ID.
     * @param id El ID del comentario.
     * @return El comentario correspondiente al ID.
     */
    @Override
    public Comment getById(Long id) {
        return commentDAO.getById(id);
    }

    /**
     * Crea un nuevo comentario.
     * @param comment El comentario a crear.
     * @return true si se creó correctamente, false de lo contrario.
     */
    @Override
    public boolean create(Comment comment) {
        return commentDAO.create(comment);
    }

    /**
     * Actualiza un comentario existente.
     * @param comment El comentario a actualizar.
     * @return true si se actualizó correctamente, false de lo contrario.
     */
    @Override
    public boolean update(Comment comment) {
        return commentDAO.update(comment);
    }

    /**
     * Elimina un comentario por su ID.
     * @param id El ID del comentario a eliminar.
     * @return true si se eliminó correctamente, false de lo contrario.
     */
    @Override
    public boolean delete(Long id) {
        return commentDAO.delete(id);
    }
}
