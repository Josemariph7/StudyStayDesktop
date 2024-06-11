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

package com.example.ejemplo.dao;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de las reservas en la base de datos.
 */
public class BookingDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    /**
     * Obtiene todas las reservas de la base de datos.
     * @return Lista de reservas
     */
    public List<Booking> getAll() {
        List<Booking> bookingList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Bookings")) {

            while (resultSet.next()) {
                Booking booking = mapBooking(resultSet);
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookingList;
    }

    /**
     * Crea una nueva reserva en la base de datos.
     * @param booking Reserva a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(Booking booking) {
        String sql = "INSERT INTO Bookings (AccommodationId, UserId, StartDate, EndDate, Status) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, booking.getAccommodation().getAccommodationId());
            statement.setLong(2, booking.getUser().getUserId());
            statement.setObject(3, booking.getStartDate());
            statement.setObject(4, booking.getEndDate());
            statement.setString(5, booking.getStatus().name());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setBookingId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating booking failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza una reserva existente en la base de datos.
     * @param booking Reserva a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(Booking booking) {
        String sql = "UPDATE Bookings SET AccommodationId=?, UserId=?, StartDate=?, EndDate=?, Status=? " +
                "WHERE BookingId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, booking.getAccommodation().getAccommodationId());
            statement.setLong(2, booking.getUser().getUserId());
            statement.setObject(3, booking.getStartDate());
            statement.setObject(4, booking.getEndDate());
            statement.setString(5, booking.getStatus().name());
            statement.setLong(6, booking.getBookingId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una reserva de la base de datos por su ID.
     * @param bookingId ID de la reserva a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long bookingId) {
        String sql = "DELETE FROM Bookings WHERE BookingId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, bookingId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto Booking.
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto Booking mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    private Booking mapBooking(ResultSet resultSet) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(resultSet.getLong("BookingId"));

        // Obtener el ID del alojamiento y del usuario desde el ResultSet
        Long accommodationId = resultSet.getLong("AccommodationId");
        Long userId = resultSet.getLong("UserId");

        // Cargar el objeto Accommodation asociado al ID
        AccommodationDAO accommodationDAO = new AccommodationDAO();
        Accommodation accommodation = accommodationDAO.getById(accommodationId);
        booking.setAccommodation(accommodation);

        // Cargar el objeto User asociado al ID
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getById(userId);
        booking.setUser(user);

        booking.setStartDate(resultSet.getObject("StartDate", LocalDateTime.class));
        booking.setEndDate(resultSet.getObject("EndDate", LocalDateTime.class));
        booking.setStatus(Booking.BookingStatus.valueOf(resultSet.getString("Status")));
        return booking;
    }

    /**
     * Obtiene todas las reservas de un usuario específico.
     * @param userId ID del usuario
     * @return Lista de reservas del usuario
     */
    public List<Booking> getByUserId(Long userId) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE UserId = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = mapBooking(resultSet);
                    bookingList.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving bookings for user: " + e.getMessage(), e);
        }
        return bookingList;
    }
}
