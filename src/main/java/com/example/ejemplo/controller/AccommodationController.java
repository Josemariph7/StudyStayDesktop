package com.example.ejemplo.controller;

import com.example.ejemplo.dao.AccommodationDAO;
import com.example.ejemplo.model.Accommodation;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de alojamientos.
 */
public class AccommodationController implements CRUD<Accommodation> {

    private final AccommodationDAO accommodationDAO = new AccommodationDAO();

    /**
     * Obtiene todos los alojamientos.
     * @return Una lista de todos los alojamientos.
     */
    @Override
    public List<Accommodation> getAll() {
        return accommodationDAO.getAll();
    }

    /**
     * Obtiene un alojamiento por su ID.
     * @param id El ID del alojamiento.
     * @return El alojamiento correspondiente al ID.
     */
    @Override
    public Accommodation getById(Long id) {
        return accommodationDAO.getById(id);
    }

    /**
     * Crea un nuevo alojamiento.
     * @param accommodation El alojamiento a crear.
     * @return true si se creó correctamente, false de lo contrario.
     */
    @Override
    public boolean create(Accommodation accommodation) {
        return accommodationDAO.create(accommodation);
    }

    /**
     * Actualiza un alojamiento existente.
     * @param accommodation El alojamiento a actualizar.
     * @return true si se actualizó correctamente, false de lo contrario.
     */
    @Override
    public boolean update(Accommodation accommodation) {
        return accommodationDAO.update(accommodation);
    }

    /**
     * Elimina un alojamiento por su ID.
     * @param id El ID del alojamiento a eliminar.
     * @return true si se eliminó correctamente, false de lo contrario.
     */
    @Override
    public boolean delete(Long id) {
        return accommodationDAO.delete(id);
    }
}
