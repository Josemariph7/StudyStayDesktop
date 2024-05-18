package com.example.ejemplo.dao;

import com.example.ejemplo.controller.UserController;
import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.Message;
import com.example.ejemplo.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de temas en el foro en la base de datos.
 */
public class ForumTopicDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    /**
     * Obtiene todos los temas del foro de la base de datos.
     * @return Lista de temas del foro
     */
    public List<ForumTopic> getAll() {
        List<ForumTopic> topics = new ArrayList<>();
        String sql = "SELECT * FROM ForumTopics";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                ForumTopic topic = mapTopic(resultSet);
                topics.add(topic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    /**
     * Crea un nuevo tema en el foro en la base de datos.
     * @param topic Tema a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(ForumTopic topic) {
        String sql = "INSERT INTO ForumTopics (Title, Description, AuthorId, DateTime) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, topic.getTitle());
            statement.setString(2, topic.getDescription());
            statement.setLong(3, topic.getAuthor().getUserId());
            statement.setObject(4, topic.getDateTime());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    topic.setTopicId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating forum topic failed, no ID obtained.");
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un tema del foro de la base de datos por su ID.
     * @param topicId ID del tema a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long topicId) {
        String sql = "DELETE FROM ForumTopics WHERE TopicId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, topicId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un tema existente en el foro en la base de datos.
     * @param topic Tema a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(ForumTopic topic) {
        String sql = "UPDATE ForumTopics SET Title=?, Description=?, AuthorId=?, DateTime=? WHERE TopicId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, topic.getTitle());
            statement.setString(2, topic.getDescription());
            statement.setLong(3, topic.getAuthor().getUserId());
            statement.setObject(4, topic.getDateTime());
            statement.setLong(5, topic.getTopicId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto ForumTopic.
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto ForumTopic mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    private ForumTopic mapTopic(ResultSet resultSet) throws SQLException {
        ForumTopic topic = new ForumTopic();
        topic.setTopicId(resultSet.getLong("TopicId"));

        // Mapeo del autor del tema
        User author = new User();
        author.setUserId(resultSet.getLong("AuthorId"));
        UserController controller= new UserController();
        author= controller.getById(author.getUserId());
        // Aquí puedes cargar el resto de los detalles del autor si es necesario
        topic.setAuthor(author);

        topic.setTitle(resultSet.getString("Title"));
        topic.setDescription(resultSet.getString("Description"));
        topic.setDateTime(resultSet.getTimestamp("DateTime").toLocalDateTime());
        topic.getComments(topic.getTopicId());
        // Los comentarios se cargarán por separado, ya que no están incluidos en este método de mapeo
        return topic;
    }

    public List<ForumComment> getComments(Long TopicId) {
        List<ForumComment> comments = new ArrayList<>();
        String sql = "SELECT * FROM ForumComments WHERE TopicId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, TopicId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ForumComment comment = new ForumComment(
                            resultSet.getLong("CommentId"),
                            resultSet.getLong("TopicId"),
                            resultSet.getLong("AuthorId"),
                            resultSet.getString("Content"),
                            resultSet.getTimestamp("DateTime").toLocalDateTime()
                    );
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Ordena los mensajes por fecha antes de devolverlos
        comments.sort((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()));
        return comments;
    }

    /**
     * Obtiene un tema del foro de la base de datos por su ID.
     * @param topicId ID del tema a obtener
     * @return Objeto ForumTopic si se encuentra, null de lo contrario
     */
    public ForumTopic getById(Long topicId) {
        String sql = "SELECT * FROM ForumTopics WHERE TopicId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, topicId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapTopic(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
