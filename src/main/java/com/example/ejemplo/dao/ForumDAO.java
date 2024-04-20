package com.example.ejemplo.dao;

import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de foros.
 */
public class ForumDAO {

    /**
     * Obtiene todos los foros de la base de datos.
     * @return Lista de foros
     */
    public List<Forum> getAll() {

        return null;
    }

    /**
     * Obtiene un foro por su ID.
     * @param id ID del foro
     * @return Foro encontrado
     */
    public Forum getById(Long id) {

        return null;
    }

    /**
     * Crea un nuevo foro en la base de datos.
     * @param forum Foro a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(Forum forum) {

        return false;
    }

    /**
     * Actualiza un foro existente en la base de datos.
     * @param forum Foro a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(Forum forum) {

        return false;
    }

    /**
     * Elimina un foro de la base de datos por su ID.
     * @param id ID del foro a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long id) {

        return false;
    }
}
