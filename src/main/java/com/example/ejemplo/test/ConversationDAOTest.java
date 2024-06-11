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

import com.example.ejemplo.dao.ConversationDAO;
import com.example.ejemplo.dao.MessageDAO;
import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.Message;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class ConversationDAOTest {

    private ConversationDAO conversationDAO;
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/StudyStayDB";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    @Before
    public void setUp() {
        conversationDAO = new ConversationDAO();
        clearDatabase();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    private void clearDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM ChatConversations")) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllConversations() {
        List<Conversation> conversations = conversationDAO.getAllConversations();
        assertNotNull(conversations);
        assertTrue(conversations.isEmpty());
    }

    @Test
    public void testCreateConversation() {
        Conversation conversation = new Conversation();
        conversation.setUser1Id(1L);
        conversation.setUser2Id(2L);
        boolean result = conversationDAO.createConversation(conversation);
        assertTrue(result);
        assertNotNull(conversation.getConversationId());
    }

    @Test
    public void testDeleteConversation() {
        Conversation conversation = new Conversation();
        conversation.setUser1Id(1L);
        conversation.setUser2Id(2L);
        conversationDAO.createConversation(conversation);
        boolean result = conversationDAO.deleteConversation(conversation.getConversationId());
        assertTrue(result);
    }

    @Test
    public void testUpdateConversation() {
        Conversation conversation = new Conversation();
        conversation.setUser1Id(1L);
        conversation.setUser2Id(2L);
        conversationDAO.createConversation(conversation);
        conversation.setUser2Id(3L);
        boolean result = conversationDAO.updateConversation(conversation);
        assertTrue(result);
        Conversation updatedConversation = conversationDAO.getAllConversations().get(0);
        assertEquals(Long.valueOf(3L), updatedConversation.getUser2Id());
    }

    @Test
    public void testGetMessagesForConversation() {
        Conversation conversation = new Conversation();
        conversation.setUser1Id(1L);
        conversation.setUser2Id(2L);
        conversationDAO.createConversation(conversation);

        Message message1 = new Message(null, conversation.getConversationId(), 1L, 2L, "Hello", LocalDateTime.now().minusDays(1));
        Message message2 = new Message(null, conversation.getConversationId(), 2L, 1L, "Hi", LocalDateTime.now());

        MessageDAO messageDAO = new MessageDAO();
        messageDAO.createMessage(message1);
        messageDAO.createMessage(message2);

        List<Message> messages = conversationDAO.getMessagesForConversation(conversation.getConversationId());
        assertNotNull(messages);
        assertEquals(2, messages.size());
        assertEquals("Hello", messages.get(0).getContent());
        assertEquals("Hi", messages.get(1).getContent());
    }
}
