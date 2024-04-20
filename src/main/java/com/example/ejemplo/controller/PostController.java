package com.example.ejemplo.controller;

import com.example.ejemplo.dao.PostDAO;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de publicaciones.
 */
public class PostController implements CRUD<Post> {

    private final PostDAO postDAO = new PostDAO();

    /**
     * Obtiene todas las publicaciones.
     * @return Lista de todas las publicaciones
     */
    @Override
    public List<Post> getAll() {
        return postDAO.getAll();
    }

    /**
     * Obtiene una publicación por su identificador.
     * @param id Identificador de la publicación
     * @return La publicación encontrada, o null si no se encuentra
     */
    @Override
    public Post getById(Long id) {
        return postDAO.getById(id);
    }

    /**
     * Crea una nueva publicación.
     * @param post La publicación a crear
     * @return true si la publicación se creó exitosamente, false de lo contrario
     */
    @Override
    public boolean create(Post post) {
        return postDAO.create(post);
    }

    /**
     * Actualiza una publicación existente.
     * @param post La publicación a actualizar
     * @return true si la publicación se actualizó exitosamente, false de lo contrario
     */
    @Override
    public boolean update(Post post) {
        return postDAO.update(post);
    }

    /**
     * Elimina una publicación por su identificador.
     * @param id Identificador de la publicación a eliminar
     * @return true si la publicación se eliminó exitosamente, false de lo contrario
     */
    @Override
    public boolean delete(Long id) {
        return postDAO.delete(id);
    }
}
