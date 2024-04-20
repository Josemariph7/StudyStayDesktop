package com.example.ejemplo.dao;

import com.example.ejemplo.model.Booking;

import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de reservas.
 */
public class BookingDAO {

    /**
     * Obtiene todas las reservas de la base de datos.
     * @return Lista de reservas
     */
    public List<Booking> getAll() {

        return null;
    }

    /**
     * Obtiene una reserva por su ID.
     * @param id ID de la reserva
     * @return Reserva encontrada
     */
    public Booking getById(Long id) {

        return null;
    }

    /**
     * Crea una nueva reserva en la base de datos.
     * @param booking Reserva a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(Booking booking) {

        return false;
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     * @param booking Reserva a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(Booking booking) {

        return false;
    }

    /**
     * Elimina una reserva de la base de datos por su ID.
     * @param id ID de la reserva a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long id) {

        return false;
    }
}
