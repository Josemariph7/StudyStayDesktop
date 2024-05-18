package com.example.ejemplo.dao;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationPhoto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccommodationPhotoDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    /**
     * Crea una nueva foto de alojamiento en la base de datos.
     *
     * @param photo Foto de alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(AccommodationPhoto photo) {
        String sql = "INSERT INTO AccommodationPhotos (AccommodationId, Photo) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, photo.getAccommodation().getAccommodationId());
            statement.setBytes(2, photo.getPhotoData());

            int rowsAffected = statement.executeUpdate();
            connection.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza una foto de alojamiento existente en la base de datos.
     *
     * @param photo Foto de alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(AccommodationPhoto photo) {
        String sql = "UPDATE AccommodationPhotos SET Photo=? WHERE PhotoId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBytes(1, photo.getPhotoData());
            statement.setLong(2, photo.getPhotoId());

            int rowsAffected = statement.executeUpdate();
            connection.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una foto de alojamiento de la base de datos por su ID.
     *
     * @param photoId ID de la foto de alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long photoId) {
        String sql = "DELETE FROM AccommodationPhotos WHERE PhotoId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, photoId);
            int rowsAffected = statement.executeUpdate();
            connection.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todas las fotos de alojamiento de la base de datos.
     *
     * @return Lista de fotos de alojamiento
     */
    public List<AccommodationPhoto> getAll() {
        List<AccommodationPhoto> photoList = new ArrayList<>();
        String sql = "SELECT * FROM AccommodationPhotos";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                AccommodationPhoto photo = mapAccommodationPhoto(resultSet);
                photoList.add(photo);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoList;
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto AccommodationPhoto.
     *
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto AccommodationPhoto mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    private static AccommodationPhoto mapAccommodationPhoto(ResultSet resultSet) throws SQLException {
        AccommodationPhoto photo = new AccommodationPhoto();
        photo.setPhotoId(resultSet.getLong("PhotoId"));
        // Aquí deberías establecer la propiedad accommodation del objeto photo si es relevante en tu caso
        // photo.setAccommodation(accommodation);
        photo.setPhotoData(resultSet.getBytes("Photo"));
        return photo;
    }
}
