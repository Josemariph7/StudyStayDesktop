package com.example.ejemplo.controller;

import com.example.ejemplo.dao.BookingDAO;
import com.example.ejemplo.model.Booking;

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
}
