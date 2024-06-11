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
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de las reseñas de alojamientos en la base de datos.
 */
public class AccommodationReviewDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    /**
     * Obtiene todas las reseñas de un alojamiento específico.
     * @param accommodationId ID del alojamiento
     * @return Lista de reseñas del alojamiento
     */
    public List<AccommodationReview> getReviewsForAccommodation(Long accommodationId) {
        List<AccommodationReview> reviewList = new ArrayList<>();
        String sql = "SELECT * FROM AccommodationReviews WHERE AccommodationId = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, accommodationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AccommodationReview review = mapReview(resultSet);
                    reviewList.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    /**
     * Obtiene todas las reseñas de alojamientos de la base de datos.
     * @return Lista de reseñas de alojamientos
     */
    public List<AccommodationReview> getAll() {
        List<AccommodationReview> reviewList = new ArrayList<>();
        String sql = "SELECT * FROM AccommodationReviews";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                AccommodationReview review = mapReview(resultSet);
                reviewList.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewList;
    }

    /**
     * Crea una nueva reseña de alojamiento en la base de datos.
     * @param review Reseña de alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(AccommodationReview review) {
        String sql = "INSERT INTO AccommodationReviews (AccommodationId, AuthorId, Rating, Comment, DateTime) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, review.getAccommodation().getAccommodationId());
            statement.setLong(2, review.getAuthor().getUserId());
            statement.setDouble(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setObject(5, review.getDateTime());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.setReviewId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating review failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza una reseña de alojamiento existente en la base de datos.
     * @param review Reseña de alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(AccommodationReview review) {
        String sql = "UPDATE AccommodationReviews SET AccommodationId=?, AuthorId=?, Rating=?, Comment=?, DateTime=? " +
                "WHERE ReviewId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, review.getAccommodation().getAccommodationId());
            statement.setLong(2, review.getAuthor().getUserId());
            statement.setDouble(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setObject(5, review.getDateTime());
            statement.setLong(6, review.getReviewId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una reseña de alojamiento de la base de datos por su ID.
     * @param reviewId ID de la reseña de alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long reviewId) {
        String sql = "DELETE FROM AccommodationReviews WHERE ReviewId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, reviewId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto AccommodationReview.
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto AccommodationReview mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    static AccommodationReview mapReview(ResultSet resultSet) throws SQLException {
        AccommodationReview review = new AccommodationReview();
        review.setReviewId(resultSet.getLong("ReviewId"));

        // Obtener el alojamiento y el autor de la reseña a partir de sus IDs
        Accommodation accommodation = AccommodationDAO.getById(resultSet.getLong("AccommodationId"));
        User author = UserDAO.getById(resultSet.getLong("AuthorId"));

        review.setAccommodation(accommodation);
        review.setAuthor(author);

        review.setRating(resultSet.getDouble("Rating"));
        review.setComment(resultSet.getString("Comment"));
        review.setDateTime(resultSet.getObject("DateTime", LocalDateTime.class));
        return review;
    }

}
