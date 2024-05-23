package com.example.ejemplo.dao;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.Message;
import com.example.ejemplo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/StudyStayDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Obtiene todas las conversaciones.
     * @return Lista de todas las conversaciones
     */
    public List<Conversation> getAllConversations() {
        List<Conversation> conversations = new ArrayList<>();
        String sql = "SELECT * FROM ChatConversations";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Conversation conversation = mapConversation(resultSet);
                System.out.println(conversation);
                conversations.add(conversation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversations;
    }

    /**
     * Crea una nueva conversación en la base de datos.
     * @param conversation La conversación a crear
     * @return true si la conversación se creó con éxito, false en caso contrario
     */
    public boolean createConversation(Conversation conversation) {
        String sql = "INSERT INTO ChatConversations (User1Id, User2Id) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, conversation.getUser1Id());
            statement.setLong(2, conversation.getUser2Id());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    conversation.setConversationId(generatedKeys.getLong(1));
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina una conversación de la base de datos por su ID.
     * @param conversationId ID de la conversación a eliminar
     * @return true si la conversación se eliminó con éxito, false en caso contrario
     */
    public boolean deleteConversation(Long conversationId) {
        String sql = "DELETE FROM ChatConversations WHERE ConversationId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, conversationId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza una conversación en la base de datos.
     * @param conversation La conversación a actualizar
     * @return true si la conversación se actualizó con éxito, false en caso contrario
     */
    public boolean updateConversation(Conversation conversation) {
        String sql = "UPDATE ChatConversations SET User1Id=?, User2Id=? WHERE ConversationId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, conversation.getUser1Id());
            statement.setLong(2, conversation.getUser2Id());
            statement.setLong(3, conversation.getConversationId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método de mapeo para convertir un ResultSet en una conversación.
     * @param resultSet ResultSet con los datos de la conversación
     * @return La conversación mapeada
     * @throws SQLException Si hay algún error al acceder a los datos del ResultSet
     */
    private Conversation mapConversation(ResultSet resultSet) throws SQLException {
        Conversation conversation = new Conversation();
        conversation.setConversationId(resultSet.getLong("ConversationId"));

        conversation.setUser1Id(resultSet.getLong("User1Id"));
        conversation.setUser2Id(resultSet.getLong("User2Id"));

        // Obtener los mensajes de la conversación
        List<Message> messages = getMessagesForConversation(conversation.getConversationId());
        conversation.setMessages(messages);

        return conversation;
    }

    /**
     * Método para obtener los mensajes de una conversación y ordenarlos por fecha.
     * @param conversationId ID de la conversación
     * @return Lista de mensajes de la conversación ordenados por fecha
     */
    public List<Message> getMessagesForConversation(Long conversationId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM ChatMessages WHERE ConversationId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, conversationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = new Message(
                            resultSet.getLong("MessageId"),
                            resultSet.getLong("ConversationId"),
                            resultSet.getLong("SenderId"),
                            resultSet.getLong("ReceiverId"),
                            resultSet.getString("Content"),
                            resultSet.getTimestamp("DateTime").toLocalDateTime()
                    );
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Ordena los mensajes por fecha antes de devolverlos
        messages.sort((m1, m2) -> m1.getDateTime().compareTo(m2.getDateTime()));
        return messages;
    }
}
