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
