package com.example.ejemplo.controller;

import com.example.ejemplo.dao.AccommodationPhotoDAO;
import com.example.ejemplo.model.AccommodationPhoto;

import java.util.List;

/**
 * Clase controladora para manejar las operaciones relacionadas con las fotos de alojamiento.
 */
public class AccommodationPhotoController {

    private final AccommodationPhotoDAO photoDAO;

    public AccommodationPhotoController() {
        this.photoDAO = new AccommodationPhotoDAO();
    }

    /**
     * Crea una nueva foto de alojamiento en la base de datos.
     *
     * @param photo Foto de alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean createPhoto(AccommodationPhoto photo) {
        return photoDAO.create(photo);
    }

    /**
     * Actualiza una foto de alojamiento existente en la base de datos.
     *
     * @param photo Foto de alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean updatePhoto(AccommodationPhoto photo) {
        return photoDAO.update(photo);
    }

    /**
     * Elimina una foto de alojamiento de la base de datos por su ID.
     *
     * @param photoId ID de la foto de alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean deletePhoto(Long photoId) {
        return photoDAO.delete(photoId);
    }

    /**
     * Obtiene todas las fotos de alojamiento de la base de datos.
     *
     * @return Lista de fotos de alojamiento
     */
    public List<AccommodationPhoto> getAllPhotos() {
        return photoDAO.getAll();
    }

}
