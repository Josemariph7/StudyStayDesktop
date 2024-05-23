package com.example.ejemplo.test;

import com.example.ejemplo.dao.ForumTopicDAO;
import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import org.junit.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.*;

public class ForumTopicDAOTest {

    private ForumTopicDAO forumTopicDAO;

    @Before
    public void setUp() {
        forumTopicDAO = new ForumTopicDAO();
    }

    @Test
    public void testGetAll() {
        List<ForumTopic> topics = forumTopicDAO.getAll();
        assertNotNull(topics);
        assertTrue(topics.size() >= 0);
    }

    @Test
    public void testCreate() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("New Topic", "New Description", author, LocalDateTime.now());
        boolean result = forumTopicDAO.create(topic);
        assertTrue(result);
        assertNotNull(topic.getTopicId());
    }

    @Test
    public void testDelete() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("Temp Topic", "Temp Description", author, LocalDateTime.now());
        forumTopicDAO.create(topic);
        boolean result = forumTopicDAO.delete(topic.getTopicId());
        assertTrue(result);
    }

    @Test
    public void testUpdate() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("Update Topic", "Update Description", author, LocalDateTime.now());
        forumTopicDAO.create(topic);
        topic.setTitle("Updated Title");
        boolean result = forumTopicDAO.update(topic);
        assertTrue(result);
        ForumTopic updatedTopic = forumTopicDAO.getById(topic.getTopicId());
        assertEquals("Updated Title", updatedTopic.getTitle());
    }

    @Test
    public void testGetById() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("Test Topic", "Test Description", author, LocalDateTime.now());
        forumTopicDAO.create(topic);
        ForumTopic result = forumTopicDAO.getById(topic.getTopicId());
        assertNotNull(result);
        assertEquals(topic.getTopicId(), result.getTopicId());
    }

    @Test
    public void testGetComments() {
        Long topicId = 1L; // You may need to set this to an existing topic ID in your test database
        List<ForumComment> comments = forumTopicDAO.getComments(topicId);
        assertNotNull(comments);
        assertTrue(comments.size() >= 0);
    }

    @After
    public void tearDown() {
        // Add any cleanup code here if needed
    }
}
