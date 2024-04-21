package com.example.ejemplo.controller;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de foros.
 */
public class ForumController implements CRUD<Forum> {

    private final ForumDAO forumDAO = new ForumDAO();

    /**
     * Obtiene todos los foros.
     * @return Una lista de todos los foros.
     */
    @Override
    public List<Forum> getAll() {
        return forumDAO.getAll();
    }

    /**
     * Obtiene un foro por su ID.
     * @param id El ID del foro a obtener.
     * @return El foro correspondiente al ID especificado.
     */
    @Override
    public Forum getById(Long id) {
        return forumDAO.getById(id);
    }

    /**
     * Crea un nuevo foro.
     * @param forum El foro a crear.
     * @return true si se creó correctamente, false de lo contrario.
     */
    @Override
    public boolean create(Forum forum) {
        return forumDAO.create(forum);
    }

    /**
     * Actualiza un foro existente.
     * @param forum El foro a actualizar.
     * @return true si se actualizó correctamente, false de lo contrario.
     */
    @Override
    public boolean update(Forum forum) {
        return forumDAO.update(forum);
    }

    /**
     * Elimina un foro por su ID.
     * @param id El ID del foro a eliminar.
     * @return true si se eliminó correctamente, false de lo contrario.
     */
    @Override
    public boolean delete(Long id) {
        return forumDAO.delete(id);
    }
}
