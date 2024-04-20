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
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {

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
        String sql = "INSERT INTO users (Name, Email, Password, Phone, Role, RegistrationDate, ProfilePicture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().toString());
            statement.setObject(6, user.getRegistrationDate());
            statement.setString(7, user.getProfilePicture());

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
        String sql = "UPDATE users SET Name=?, Email=?, Password=?, Phone=?, Role=?, RegistrationDate=?, " +
                "ProfilePicture=? WHERE UserId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getRole().toString());
            statement.setObject(6, user.getRegistrationDate());
            statement.setString(7, user.getProfilePicture());
            statement.setLong(8, user.getUserId());

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
        String sql = "DELETE FROM users WHERE UserId=?";
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
    private User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getLong("UserId"));
        user.setName(resultSet.getString("Name"));
        user.setEmail(resultSet.getString("Email"));
        user.setPassword(resultSet.getString("Password"));
        user.setPhone(resultSet.getString("Phone"));
        user.setRole(User.UserRole.valueOf(resultSet.getString("Role")));
        user.setRegistrationDate(resultSet.getTimestamp("RegistrationDate").toLocalDateTime());
        user.setProfilePicture(resultSet.getString("ProfilePicture"));
        return user;
    }
}
