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

package com.example.ejemplo.test;

import com.example.ejemplo.dao.MessageDAO;
import com.example.ejemplo.model.Message;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class MessageDAOTest {

    private static MessageDAO messageDAO;

    @BeforeClass
    public static void setUpClass() {
        messageDAO = new MessageDAO();
        setUpDatabase();
    }

    @AfterClass
    public static void tearDownClass() {
        tearDownDatabase();
    }

    @Before
    public void setUp() {
        clearDatabase();
    }

    @Test
    public void testGetMessagesByConversation() {
        Message message = createMessage(1L, 1L, 2L);
        messageDAO.createMessage(message);

        List<Message> messages = messageDAO.getMessagesByConversation(1L);
        assertNotNull(messages);
        assertEquals(1, messages.size());
    }

    @Test
    public void testCreateMessage() {
        Message message = createMessage(1L, 1L, 2L);
        boolean result = messageDAO.createMessage(message);
        assertTrue(result);

        List<Message> messages = messageDAO.getAllMessages();
        assertEquals(1, messages.size());
    }

    @Test
    public void testModifyMessage() {
        Message message = createMessage(1L, 1L, 2L);
        messageDAO.createMessage(message);

        message.setContent("Updated Content");
        boolean result = messageDAO.modifyMessage(message);
        assertTrue(result);

        Message updatedMessage = messageDAO.getMessagesByConversation(1L).get(0);
        assertEquals("Updated Content", updatedMessage.getContent());
    }

    @Test
    public void testDeleteMessage() {
        Message message = createMessage(1L, 1L, 2L);
        messageDAO.createMessage(message);

        boolean result = messageDAO.deleteMessage(message.getMessageId());
        assertTrue(result);

        List<Message> messages = messageDAO.getAllMessages();
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testGetAllMessages() {
        Message message1 = createMessage(1L, 1L, 2L);
        Message message2 = createMessage(2L, 1L, 2L);
        messageDAO.createMessage(message1);
        messageDAO.createMessage(message2);

        List<Message> messages = messageDAO.getAllMessages();
        assertNotNull(messages);
        assertEquals(2, messages.size());
    }

    private Message createMessage(Long conversationId, Long senderId, Long receiverId) {
        return new Message(null, conversationId, senderId, receiverId, "Test Message", LocalDateTime.now());
    }

    private static void setUpDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS StudyStayDB");
            statement.executeUpdate("USE StudyStayDB");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS ChatMessages (" +
                    "MessageId BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "ConversationId BIGINT, " +
                    "SenderId BIGINT, " +
                    "ReceiverId BIGINT, " +
                    "Content TEXT, " +
                    "DateTime TIMESTAMP)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tearDownDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS StudyStayDB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearDatabase() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudyStayDB", "root", "");
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM ChatMessages");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
