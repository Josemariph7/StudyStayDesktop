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
