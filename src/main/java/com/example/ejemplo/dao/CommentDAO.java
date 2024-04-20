package com.example.ejemplo.dao;

import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de comentarios.
 */
public class CommentDAO {

    /**
     * Obtiene todos los comentarios de la base de datos.
     * @return Lista de comentarios
     */
    public List<Comment> getAll() {

        return null;
    }

    /**
     * Obtiene un comentario por su ID.
     * @param id ID del comentario
     * @return Comentario encontrado
     */
    public Comment getById(Long id) {

        return null;
    }

    /**
     * Crea un nuevo comentario en la base de datos.
     * @param comment Comentario a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(Comment comment) {

        return false;
    }

    /**
     * Actualiza un comentario existente en la base de datos.
     * @param comment Comentario a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(Comment comment) {

        return false;
    }

    /**
     * Elimina un comentario de la base de datos por su ID.
     * @param id ID del comentario a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long id) {

        return false;
    }
}
