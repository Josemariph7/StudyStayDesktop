package com.example.ejemplo.dao;

import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO para gestionar las operaciones CRUD de comentarios en el foro en la base de datos.
 */
public class ForumCommentDAO {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studystaydb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Nombre de usuario de la base de datos
    private static final String PASSWORD = ""; // Contraseña de la base de datos

    /**
     * Obtiene todos los comentarios de un tema específico del foro.
     * @param topicId ID del tema del foro
     * @return Lista de comentarios del tema del foro
     */
    public List<ForumComment> getCommentsByTopic(Long topicId) {
        List<ForumComment> comments = new ArrayList<>();
        String sql = "SELECT * FROM ForumComments WHERE TopicId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, topicId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ForumComment comment = mapComment(resultSet);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    /**
     * Crea un nuevo comentario en la base de datos.
     * @param comment Comentario a crear
     * @return true si se creó correctamente, false de lo contrario
     */
    public boolean create(ForumComment comment) {
        String sql = "INSERT INTO ForumComments (TopicId, AuthorId, Content, DateTime) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, comment.getTopic().getTopicId());
            statement.setLong(2, comment.getAuthor().getUserId());
            statement.setString(3, comment.getContent());
            statement.setObject(4, comment.getDateTime());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un comentario de la base de datos por su ID.
     * @param commentId ID del comentario a eliminar
     * @return true si se eliminó correctamente, false de lo contrario
     */
    public boolean delete(Long commentId) {
        String sql = "DELETE FROM ForumComments WHERE CommentId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, commentId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un comentario existente en la base de datos.
     * @param comment Comentario a actualizar
     * @return true si se actualizó correctamente, false de lo contrario
     */
    public boolean update(ForumComment comment) {
        String sql = "UPDATE ForumComments SET TopicId=?, AuthorId=?, Content=?, DateTime=? WHERE CommentId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, comment.getTopic().getTopicId());
            statement.setLong(2, comment.getAuthor().getUserId());
            statement.setString(3, comment.getContent());
            statement.setObject(4, comment.getDateTime());
            statement.setLong(5, comment.getCommentId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mapea un conjunto de resultados de una consulta a un objeto ForumComment.
     * @param resultSet Conjunto de resultados de la consulta SQL
     * @return Objeto ForumComment mapeado
     * @throws SQLException Si hay un error al acceder a los datos en el ResultSet
     */
    private ForumComment mapComment(ResultSet resultSet) throws SQLException {
        ForumComment comment = new ForumComment();
        comment.setCommentId(resultSet.getLong("CommentId"));

        // Mapeo del tema del comentario
        ForumTopic topic = new ForumTopic();
        topic.setTopicId(resultSet.getLong("TopicId"));
        // Aquí puedes cargar el resto de los detalles del tema si es necesario
        comment.setTopic(topic);

        // Mapeo del autor del comentario
        User author = new User();
        author.setUserId(resultSet.getLong("AuthorId"));
        // Aquí puedes cargar el resto de los detalles del autor si es necesario
        comment.setAuthor(author);

        comment.setContent(resultSet.getString("Content"));
        comment.setDateTime(resultSet.getTimestamp("DateTime").toLocalDateTime());
        return comment;
    }

    /**
     * Obtiene todos los comentarios de la base de datos.
     * @return Lista de todos los comentarios
     */
    public List<ForumComment> getAll() {
        List<ForumComment> comments = new ArrayList<>();
        String sql = "SELECT * FROM ForumComments";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                ForumComment comment = mapComment(resultSet);
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

}
