package com.example.ejemplo.dao;

import com.example.ejemplo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de usuarios en la base de datos.
 */
public class UserDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    /**
     * Obtiene todos los usuarios de la base de datos.
     * @return Lista de usuarios
     */
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Users")) {

            while (resultSet.next()) {
                User user = mapUser(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     * @param user Usuario a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(User user) {
        String sql = "INSERT INTO Users (Name, LastName, Email, Password, Phone, BirthDate, RegistrationDate, " +
                "Gender, DNI, ProfilePicture, Bio, isAdmin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhone());
            statement.setObject(6, user.getBirthDate());
            statement.setObject(7, user.getRegistrationDate()); // Nuevo campo
            statement.setString(8, user.getGender().toString());
            statement.setString(9, user.getDni());
            statement.setBytes(10, user.getProfilePicture());
            statement.setString(11, user.getBio());
            statement.setBoolean(12, user.isAdmin());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un usuario existente en la base de datos.
     * @param user Usuario a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(User user) {
        String sql = "UPDATE Users SET Name=?, LastName=?, Email=?, Password=?, Phone=?, BirthDate=?, RegistrationDate=?, " +
                "Gender=?, DNI=?, ProfilePicture=?, Bio=?, isAdmin=? WHERE UserId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhone());
            statement.setObject(6, user.getBirthDate());
            statement.setObject(7, user.getRegistrationDate()); // Nuevo campo
            statement.setString(8, user.getGender().toString());
            statement.setString(9, user.getDni());
            statement.setBytes(10, user.getProfilePicture());
            statement.setString(11, user.getBio());
            statement.setBoolean(12, user.isAdmin());
            statement.setLong(13, user.getUserId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     * @param userId ID del usuario a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long userId) {
        String sql = "DELETE FROM Users WHERE UserId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto User.
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto User mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    public static User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("UserId"));
        user.setName(resultSet.getString("Name"));
        user.setLastName(resultSet.getString("LastName"));
        user.setEmail(resultSet.getString("Email"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhone(resultSet.getString("Phone"));
        user.setBirthDate(resultSet.getTimestamp("BirthDate").toLocalDateTime());
        user.setRegistrationDate(resultSet.getTimestamp("RegistrationDate").toLocalDateTime()); // Nuevo campo
        user.setGender(User.Gender.valueOf(resultSet.getString("Gender")));
        user.setDni(resultSet.getString("DNI"));
        user.setProfilePicture(resultSet.getBytes("ProfilePicture"));
        user.setBio(resultSet.getString("Bio"));
        user.setAdmin(resultSet.getBoolean("isAdmin"));
        return user;
    }

    public static User getById(Long userId) {
        String sql = "SELECT * FROM Users WHERE UserId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean authenticate(String email, String password) {
        String sql = "SELECT * FROM Users WHERE Email=? AND Password=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Si hay algún resultado, las credenciales son válidas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
