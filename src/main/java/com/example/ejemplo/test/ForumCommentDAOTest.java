package com.example.ejemplo.test;

import com.example.ejemplo.dao.ForumCommentDAO;
import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import org.junit.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.Assert.*;

public class ForumCommentDAOTest {

    private ForumCommentDAO forumCommentDAO;

    @Before
    public void setUp() {
        forumCommentDAO = new ForumCommentDAO();
    }

    @Test
    public void testGetAll() {
        List<ForumComment> comments = forumCommentDAO.getAll();
        assertNotNull(comments);
        assertTrue(comments.size() >= 0);
    }

    @Test
    public void testCreate() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("New Topic", "New Description", author, LocalDateTime.now());
        ForumComment comment = new ForumComment(topic, author, "New Comment", LocalDateTime.now());
        boolean result = forumCommentDAO.create(comment);
        assertTrue(result);
        assertNotNull(comment.getCommentId());
    }

    @Test
    public void testDelete() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("Temp Topic", "Temp Description", author, LocalDateTime.now());
        ForumComment comment = new ForumComment(topic, author, "Temp Comment", LocalDateTime.now());
        forumCommentDAO.create(comment);
        boolean result = forumCommentDAO.delete(comment.getCommentId());
        assertTrue(result);
    }

    @Test
    public void testUpdate() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("Update Topic", "Update Description", author, LocalDateTime.now());
        ForumComment comment = new ForumComment(topic, author, "Update Comment", LocalDateTime.now());
        forumCommentDAO.create(comment);
        comment.setContent("Updated Comment");
        boolean result = forumCommentDAO.update(comment);
        assertTrue(result);
        ForumComment updatedComment = forumCommentDAO.getCommentsByTopic(topic.getTopicId()).stream()
                .filter(c -> c.getCommentId().equals(comment.getCommentId()))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedComment);
        assertEquals("Updated Comment", updatedComment.getContent());
    }

    @Test
    public void testGetCommentsByTopic() {
        User author = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        ForumTopic topic = new ForumTopic("Test Topic", "Test Description", author, LocalDateTime.now());
        ForumComment comment = new ForumComment(topic, author, "Test Comment", LocalDateTime.now());
        forumCommentDAO.create(comment);
        List<ForumComment> comments = forumCommentDAO.getCommentsByTopic(topic.getTopicId());
        assertNotNull(comments);
        assertTrue(comments.size() > 0);
        assertEquals(comment.getContent(), comments.get(0).getContent());
    }

    @After
    public void tearDown() {
        // Add any cleanup code here if needed
    }
}
