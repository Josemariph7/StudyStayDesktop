package com.example.ejemplo.dao;

import com.example.ejemplo.controller.AccommodationReviewController;
import com.example.ejemplo.controller.UserController;
import com.example.ejemplo.model.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase DAO para gestionar las operaciones CRUD de alojamientos en la base de datos.
 */
public class AccommodationDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    private static Connection connection;

    public AccommodationDAO() {
        try {
            this.connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene todos los alojamientos de la base de datos.
     *
     * @return Lista de alojamientos
     */
    public List<Accommodation> getAll() {
        List<Accommodation> accommodationList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Accommodations")) {

            while (resultSet.next()) {
                Accommodation accommodation = mapAccommodation(resultSet);
                accommodationList.add(accommodation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accommodationList;
    }

    /**
     * Crea un nuevo alojamiento en la base de datos.
     *
     * @param accommodation Alojamiento a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(Accommodation accommodation) {
        String sql = "INSERT INTO Accommodations (OwnerId, Address, City, Price, Description, Capacity, Services, Availability, Rating) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, accommodation.getOwner().getUserId());
            statement.setString(2, accommodation.getAddress());
            statement.setString(3, accommodation.getCity());
            statement.setBigDecimal(4, accommodation.getPrice());
            statement.setString(5, accommodation.getDescription());
            statement.setInt(6, accommodation.getCapacity());
            statement.setString(7, accommodation.getServices());
            statement.setBoolean(8, accommodation.isAvailability());
            statement.setDouble(9, accommodation.getRating());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    accommodation.setAccommodationId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating accommodation failed, no ID obtained.");
                }
            }

            // Ahora, insertamos las fotos asociadas al alojamiento
            List<AccommodationPhoto> photoList = accommodation.getPhotos();
            if (photoList != null && !photoList.isEmpty()) {
                for (AccommodationPhoto photo : photoList) {
                    String insertPhotoSql = "INSERT INTO AccommodationPhotos (AccommodationId, Photo) VALUES (?, ?)";
                    try (PreparedStatement photoStatement = connection.prepareStatement(insertPhotoSql)) {
                        photoStatement.setLong(1, accommodation.getAccommodationId());
                        photoStatement.setBytes(2, photo.getPhotoData());
                        photoStatement.executeUpdate();
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un alojamiento existente en la base de datos.
     *
     * @param accommodation Alojamiento a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(Accommodation accommodation) {
        String sql = "UPDATE Accommodations SET OwnerId=?, Address=?, City=?, Price=?, Description=?, " +
                "Capacity=?, Services=?, Availability=?, Rating=? WHERE AccommodationId=?";
        try (
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, accommodation.getOwner().getUserId());
            statement.setString(2, accommodation.getAddress());
            statement.setString(3, accommodation.getCity());
            statement.setBigDecimal(4, accommodation.getPrice());
            statement.setString(5, accommodation.getDescription());
            statement.setInt(6, accommodation.getCapacity());
            statement.setString(7, accommodation.getServices());
            statement.setBoolean(8, accommodation.isAvailability());
            statement.setDouble(9, accommodation.getRating());
            statement.setLong(10, accommodation.getAccommodationId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
/*
            // Eliminamos las fotos antiguas asociadas al alojamiento
            String deletePhotosSql = "DELETE FROM AccommodationPhotos WHERE AccommodationId=?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deletePhotosSql)) {
                deleteStatement.setLong(1, accommodation.getAccommodationId());
                deleteStatement.executeUpdate();
            }

            // Ahora, insertamos las fotos asociadas al alojamiento
            List<AccommodationPhoto> photoList = accommodation.getPhotos();
            if (photoList != null && !photoList.isEmpty()) {
                for (AccommodationPhoto photo : photoList) {
                    String insertPhotoSql = "INSERT INTO AccommodationPhotos (AccommodationId, Photo) VALUES (?, ?)";
                    try (PreparedStatement photoStatement = connection.prepareStatement(insertPhotoSql)) {
                        photoStatement.setLong(1, accommodation.getAccommodationId());
                        photoStatement.setBytes(2, photo.getPhotoData());
                        photoStatement.executeUpdate();
                    }
                }
            }
*/
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un alojamiento de la base de datos por su ID.
     *
     * @param accommodationId ID del alojamiento a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long accommodationId) {
        String deleteAccommodationSql = "DELETE FROM Accommodations WHERE AccommodationId=?";
        String deletePhotosSql = "DELETE FROM AccommodationPhotos WHERE AccommodationId=?";
        try (
             PreparedStatement deleteAccommodationStatement = connection.prepareStatement(deleteAccommodationSql);
             PreparedStatement deletePhotosStatement = connection.prepareStatement(deletePhotosSql)) {

            // Eliminamos las fotos asociadas al alojamiento
            deletePhotosStatement.setLong(1, accommodationId);
            deletePhotosStatement.executeUpdate();

            // Eliminamos el alojamiento
            deleteAccommodationStatement.setLong(1, accommodationId);
            int rowsAffected = deleteAccommodationStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto Accommodation.
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto Accommodation mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    private static Accommodation mapAccommodation(ResultSet resultSet) throws SQLException {
        Accommodation accommodation = new Accommodation();
        accommodation.setAccommodationId(resultSet.getLong("AccommodationId"));

        // Mapeo del propietario
        long ownerId = resultSet.getLong("OwnerId");
        User owner = UserDAO.getById(ownerId); // Llamamos al método estático getUserById de UserDAO
        accommodation.setOwner(owner);

        // Mapeo de los inquilinos
        UserController userController = new UserController();
        List<User> tenants = userController.getAll();
        List<Booking>bookings;
        if (tenants != null && !tenants.isEmpty()) {
            for (User user : tenants) {
                bookings = user.getBookings();
                if(bookings != null && !bookings.isEmpty()){
                    for (Booking booking : bookings) {
                        if (Objects.equals(booking.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                            accommodation.addTenant(user);
                        }
                    }
                }
            }
        }
        accommodation.setAddress(resultSet.getString("Address"));
        accommodation.setCity(resultSet.getString("City"));
        accommodation.setPrice(resultSet.getBigDecimal("Price"));
        accommodation.setDescription(resultSet.getString("Description"));
        accommodation.setCapacity(resultSet.getInt("Capacity"));
        accommodation.setServices(resultSet.getString("Services"));
        accommodation.setAvailability(resultSet.getBoolean("Availability"));
        accommodation.setRating(resultSet.getDouble("Rating"));

        // Mapeo de las fotos del alojamiento
        List<AccommodationPhoto> photoList = getPhotosForAccommodation(accommodation.getAccommodationId()); // Llamamos al método estático getPhotosForAccommodation
        accommodation.setPhotos(photoList);


        /*
        *
        * ERROR AQUI
        *
        * */
        /*
        AccommodationReviewController accommodationReviewController = new AccommodationReviewController();
        List<AccommodationReview> reviews=accommodationReviewController.getAllReviews();
        if(reviews != null && !reviews.isEmpty()){
            for (AccommodationReview review : reviews) {
                if(Objects.equals(review.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())){
                    accommodation.getReviews().add(review);
                }
            }
        }

        accommodation.setReviews(reviews);*/

        return accommodation;
    }

    /**
     * Obtiene las fotos asociadas a un alojamiento específico.
     *
     * @param accommodationId ID del alojamiento
     * @return Lista de fotos asociadas al alojamiento
     */
    public static List<AccommodationPhoto> getPhotosForAccommodation(Long accommodationId) {
        List<AccommodationPhoto> photoList = new ArrayList<>();
        String sql = "SELECT PhotoData FROM AccommodationPhotos WHERE AccommodationId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, accommodationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    byte[] photoData = resultSet.getBytes("PhotoData");
                    AccommodationPhoto photo = new AccommodationPhoto(AccommodationDAO.getById(accommodationId), photoData); // Como el ID de la foto no está disponible aquí, pasamos null
                    photoList.add(photo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photoList;
    }

    /**
     * Obtiene un alojamiento de la base de datos por su ID.
     * @param accommodationId Id del alojamiento a recuperar
     * @return El alojamiento si se encuentra, o null si no se encuentra
     */
    public static Accommodation getById(Long accommodationId) {
        String sql = "SELECT * FROM Accommodations WHERE AccommodationId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, accommodationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAccommodation(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Obtiene una lista de alojamientos que pertenecen a un propietario específico.
     * @param ownerId ID del propietario
     * @return Lista de alojamientos del propietario
     */
    public List<Accommodation> getByOwner(Long ownerId) {
        List<Accommodation> accommodationList = new ArrayList<>();
        String sql = "SELECT * FROM Accommodations WHERE OwnerId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, ownerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Accommodation accommodation = mapAccommodation(resultSet);
                    accommodationList.add(accommodation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accommodationList;
    }

    /**
     * Obtiene las reseñas de un alojamiento específico.
     * @param accommodationId ID del alojamiento
     * @return Lista de reseñas del alojamiento
     */
    public static List<AccommodationReview> getReviewsByAccommodation(Long accommodationId) {
        List<AccommodationReview> reviews = new ArrayList<>();
        String sql = "SELECT * FROM AccommodationReviews WHERE AccommodationId=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, accommodationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AccommodationReview review = AccommodationReviewDAO.mapReview(resultSet);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Obtiene todos los inquilinos de un alojamiento específico.
     * @param accommodationId ID del alojamiento
     * @return Lista de inquilinos del alojamiento
     */
    public static List<User> getTenantsForAccommodation(Long accommodationId) {
       List<User> tenants = new ArrayList<>();
       Accommodation accommodation=getById(accommodationId);
        assert accommodation != null;
        tenants=accommodation.getTenants();
        return tenants;
    }

}
