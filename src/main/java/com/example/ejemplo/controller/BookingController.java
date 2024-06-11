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

import com.example.ejemplo.dao.BookingDAO;
import com.example.ejemplo.model.Booking;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase controladora para gestionar las operaciones de reservas.
 */
public class BookingController {

    private final BookingDAO bookingDAO;

    public BookingController() {
        this.bookingDAO = new BookingDAO();
    }

    /**
     * Obtiene todas las reservas.
     * @return Lista de reservas
     */
    public List<Booking> getAllBookings() {
        return bookingDAO.getAll();
    }

    /**
     * Crea una nueva reserva.
     * @param booking Reserva a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean createBooking(Booking booking) {
        return bookingDAO.create(booking);
    }

    /**
     * Actualiza una reserva existente.
     * @param booking Reserva a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean updateBooking(Booking booking) {
        return bookingDAO.update(booking);
    }

    /**
     * Elimina una reserva por su ID.
     * @param bookingId ID de la reserva a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean deleteBooking(Long bookingId) {
        return bookingDAO.delete(bookingId);
    }

    /**
     * Obtiene todas las reservas de un usuario.
     * @param userId ID del usuario
     * @return Lista de reservas del usuario
     */
    public List<Booking> getBookingsByUser(Long userId) throws SQLException, SQLException {
        return bookingDAO.getByUserId(userId);
    }
}
