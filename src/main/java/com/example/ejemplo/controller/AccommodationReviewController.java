/*
 * StudyStay © 2024
 *
 * All rights reserved.
 *
 * This software and associated documentation files (the "Software") are owned by StudyStay. Unauthorized copying, distribution, or modification of this Software is strictly prohibited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this Software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * StudyStay
 * José María Pozo Hidalgo
 * Email: josemariph7@gmail.com
 *
 *
 */

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
