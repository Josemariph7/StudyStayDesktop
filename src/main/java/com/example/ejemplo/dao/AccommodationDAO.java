package com.example.ejemplo.dao;

import com.example.ejemplo.model.Accommodation;

import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de alojamientos.
 */
public class AccommodationDAO {

    /**
     * Obtiene todos los alojamientos de la base de datos.
     * @return Lista de alojamientos
     */
    public List<Accommodation> getAll() {

        return null;
    }

    /**
     * Obtiene un alojamiento por su ID.
     * @param id ID del alojamiento
     * @return Alojamiento encontrado
     */
    public Accommodation getById(Long id) {

        return null;
    }

    /**
     * Crea un nuevo alojamiento en la base de datos.
     * @param accommodation Alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(Accommodation accommodation) {

        return false;
    }

    /**
     * Actualiza un alojamiento existente en la base de datos.
     * @param accommodation Alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(Accommodation accommodation) {

        return false;
    }

    /**
     * Elimina un alojamiento de la base de datos por su ID.
     * @param id ID del alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long id) {

        return false;
    }
}
