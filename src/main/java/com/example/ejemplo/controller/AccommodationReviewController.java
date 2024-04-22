package com.example.ejemplo.controller;

import com.example.ejemplo.dao.AccommodationReviewDAO;
import com.example.ejemplo.model.AccommodationReview;

import java.util.List;

/**
 * Clase controladora para gestionar las operaciones de las reseñas de alojamientos.
 */
public class AccommodationReviewController {

    private final AccommodationReviewDAO accommodationReviewDAO;

    public AccommodationReviewController() {
        this.accommodationReviewDAO = new AccommodationReviewDAO();
    }

    /**
     * Obtiene todas las reseñas de alojamientos.
     * @return Lista de reseñas de alojamientos
     */
    public List<AccommodationReview> getAllReviews() {
        return accommodationReviewDAO.getAll();
    }

    /**
     * Crea una nueva reseña de alojamiento.
     * @param review Reseña de alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean createReview(AccommodationReview review) {
        return accommodationReviewDAO.create(review);
    }

    /**
     * Actualiza una reseña de alojamiento existente.
     * @param review Reseña de alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean updateReview(AccommodationReview review) {
        return accommodationReviewDAO.update(review);
    }

    /**
     * Elimina una reseña de alojamiento por su ID.
     * @param reviewId ID de la reseña de alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean deleteReview(Long reviewId) {
        return accommodationReviewDAO.delete(reviewId);
    }
}
