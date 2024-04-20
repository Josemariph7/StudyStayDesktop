package com.example.ejemplo.controller;

import com.example.ejemplo.dao.BookingDAO;
import com.example.ejemplo.model.Booking;

import java.util.List;

/**
 * Controlador para gestionar las operaciones CRUD de reservas.
 */
public class BookingController implements CRUD<Booking> {

    private final BookingDAO bookingDAO = new BookingDAO();

    /**
     * Obtiene todas las reservas.
     * @return Una lista de todas las reservas.
     */
    @Override
    public List<Booking> getAll() {
        return bookingDAO.getAll();
    }

    /**
     * Obtiene una reserva por su ID.
     * @param id El ID de la reserva.
     * @return La reserva correspondiente al ID.
     */
    @Override
    public Booking getById(Long id) {
        return bookingDAO.getById(id);
    }

    /**
     * Crea una nueva reserva.
     * @param booking La reserva a crear.
     * @return true si se creó correctamente, false de lo contrario.
     */
    @Override
    public boolean create(Booking booking) {
        return bookingDAO.create(booking);
    }

    /**
     * Actualiza una reserva existente.
     * @param booking La reserva a actualizar.
     * @return true si se actualizó correctamente, false de lo contrario.
     */
    @Override
    public boolean update(Booking booking) {
        return bookingDAO.update(booking);
    }

    /**
     * Elimina una reserva por su ID.
     * @param id El ID de la reserva a eliminar.
     * @return true si se eliminó correctamente, false de lo contrario.
     */
    @Override
    public boolean delete(Long id) {
        return bookingDAO.delete(id);
    }
}
