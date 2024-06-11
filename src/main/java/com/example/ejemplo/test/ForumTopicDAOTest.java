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
