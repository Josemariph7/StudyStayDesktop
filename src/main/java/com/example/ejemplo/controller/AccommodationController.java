package com.example.ejemplo.controller;

import com.example.ejemplo.dao.AccommodationDAO;
import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.User;

import java.util.List;

/**
 * Clase controladora para manejar las operaciones relacionadas con los alojamientos.
 */
public class AccommodationController {

    private final AccommodationDAO accommodationDAO;

    public AccommodationController() {
        this.accommodationDAO = new AccommodationDAO();
    }

    /**
     * Obtiene todos los alojamientos de la base de datos.
     * @return Lista de alojamientos
     */
    public List<Accommodation> getAllAccommodations() {
        return accommodationDAO.getAll();
    }

    /**
     * Crea un nuevo alojamiento en la base de datos.
     * @param accommodation Alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean createAccommodation(Accommodation accommodation) {
        return accommodationDAO.create(accommodation);
    }

    /**
     * Actualiza un alojamiento existente en la base de datos.
     * @param accommodation Alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean updateAccommodation(Accommodation accommodation) {
        return accommodationDAO.update(accommodation);
    }

    /**
     * Elimina un alojamiento de la base de datos por su ID.
     * @param accommodationId ID del alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean deleteAccommodation(Long accommodationId) {
        return accommodationDAO.delete(accommodationId);
    }

    /**
     * Obtiene un alojamiento de la base de datos por su ID.
     * @param accommodationId ID del alojamiento a recuperar
     * @return El alojamiento si se encuentra, o null si no se encuentra
     */
    public Accommodation getAccommodationById(Long accommodationId) {
        return AccommodationDAO.getById(accommodationId);
    }

    /**
     * Obtiene una lista de alojamientos que pertenecen a un propietario específico.
     * @param ownerId ID del propietario
     * @return Lista de alojamientos del propietario
     */
    public List<Accommodation> getAccommodationsByOwner(Long ownerId) {
        return accommodationDAO.getByOwner(ownerId);
    }

    /**
     * Obtiene las reseñas de un alojamiento específico.
     * @param accommodationId ID del alojamiento
     * @return Lista de reseñas del alojamiento
     */
    public List<AccommodationReview> getReviewsByAccommodation(Long accommodationId) {
        return AccommodationDAO.getReviewsByAccommodation(accommodationId);
    }

    /**
     * Obtiene todos los inquilinos de un alojamiento específico.
     * @param accommodationId ID del alojamiento
     * @return Lista de inquilinos del alojamiento
     */
    public List<User> getTenantsForAccommodation(Long accommodationId) {
        return AccommodationDAO.getTenantsForAccommodation(accommodationId);
    }


}
