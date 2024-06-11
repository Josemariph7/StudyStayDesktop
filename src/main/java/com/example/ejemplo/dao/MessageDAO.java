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

import com.example.ejemplo.model.Message;
import com.example.ejemplo.model.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/StudyStayDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    /**
     * Obtiene todos los mensajes de una conversación.
     *
     * @param conversationId ID de la conversación
     * @return Lista de mensajes de la conversación
     */
    public List<Message> getMessagesByConversation(Long conversationId) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM ChatMessages WHERE ConversationId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, conversationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Message message = mapMessage(resultSet);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * Crea un nuevo mensaje en la base de datos.
     *
     * @param message El mensaje a crear
     * @return true si se creó correctamente, false si hubo algún error
     */
    public boolean createMessage(Message message) {
        String sql = "INSERT INTO ChatMessages (ConversationId, SenderId, ReceiverId, Content, DateTime) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, message.getConversationId());
            statement.setLong(2, message.getSenderId());
            statement.setLong(3, message.getReceiverId());
            statement.setString(4, message.getContent());
            statement.setTimestamp(5, Timestamp.valueOf(message.getDateTime()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un mensaje de la base de datos.
     *
     * @param messageId ID del mensaje a eliminar
     * @return true si se eliminó correctamente, false si hubo algún error
     */
    public boolean deleteMessage(Long messageId) {
        String sql = "DELETE FROM ChatMessages WHERE MessageId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, messageId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modifica un mensaje en la base de datos.
     *
     * @param message El mensaje modificado
     * @return true si se modificó correctamente, false si hubo algún error
     */
    public boolean modifyMessage(Message message) {
        String sql = "UPDATE ChatMessages SET Content=?, DateTime=? WHERE MessageId=?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, message.getContent());
            statement.setTimestamp(2, Timestamp.valueOf(message.getDateTime()));
            statement.setLong(3, message.getMessageId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene todos los mensajes de la base de datos.
     *
     * @return Lista de todos los mensajes
     */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM ChatMessages";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Message message = mapMessage(resultSet);
                messages.add(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    // Método de mapeo del mensaje
    private Message mapMessage(ResultSet resultSet) throws SQLException {
        Long messageId = resultSet.getLong("MessageId");
        Long conversationId = resultSet.getLong("ConversationId");
        Long senderId = resultSet.getLong("SenderId");
        Long receiverId = resultSet.getLong("ReceiverId");
        String content = resultSet.getString("Content");
        LocalDateTime dateTime = resultSet.getTimestamp("DateTime").toLocalDateTime();
        return new Message(messageId, conversationId, senderId, receiverId, content, dateTime);
    }
}
