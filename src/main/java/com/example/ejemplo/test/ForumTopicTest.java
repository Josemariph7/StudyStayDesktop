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

import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import com.example.ejemplo.model.ForumComment;
import org.junit.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ForumTopicTest {

    private ForumTopic forumTopic;
    private ForumTopic anotherForumTopic;

    @Before
    public void setUp() {
        User author = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", null, User.Gender.MALE, "12345678X", true);
        forumTopic = new ForumTopic("Title 1", "Description 1", author, LocalDateTime.of(2023, 5, 23, 12, 30));
        anotherForumTopic = new ForumTopic("Title 2", "Description 2", author, LocalDateTime.of(2023, 5, 23, 12, 30));
    }


    @Test
    public void testGetAndSetTitle() {
        forumTopic.setTitle("New Title");
        assertEquals("New Title", forumTopic.getTitle());
    }

    @Test
    public void testGetAndSetDescription() {
        forumTopic.setDescription("New Description");
        assertEquals("New Description", forumTopic.getDescription());
    }

    @Test
    public void testGetAndSetAuthor() {
        User newAuthor = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", null, User.Gender.FEMALE, "87654321X", false);
        forumTopic.setAuthor(newAuthor);
        assertEquals(newAuthor, forumTopic.getAuthor());
    }

    @Test
    public void testGetAndSetDateTime() {
        LocalDateTime newDateTime = LocalDateTime.of(2024, 5, 23, 12, 30);
        forumTopic.setDateTime(newDateTime);
        assertEquals(newDateTime, forumTopic.getDateTime());
    }

    @Test
    public void testGetAndSetComments() {
        ForumComment comment1 = new ForumComment();
        ForumComment comment2 = new ForumComment();
        List<ForumComment> comments = Arrays.asList(comment1, comment2);
        forumTopic.setComments(comments);
        assertEquals(comments, forumTopic.getComments(1L));
    }

    @Test
    public void testEquals() {
        ForumTopic anotherForumTopicCopy = new ForumTopic("Title 2", "Description 2", forumTopic.getAuthor(), LocalDateTime.of(2023, 5, 23, 12, 30));
        assertEquals(anotherForumTopic, anotherForumTopicCopy);
        assertNotEquals(forumTopic, anotherForumTopic);
    }

    @Test
    public void testHashCode() {
        ForumTopic anotherForumTopicCopy = new ForumTopic("Title 2", "Description 2", forumTopic.getAuthor(), LocalDateTime.of(2023, 5, 23, 12, 30));
        assertEquals(anotherForumTopic.hashCode(), anotherForumTopicCopy.hashCode());
        assertNotEquals(forumTopic.hashCode(), anotherForumTopic.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "ForumTopic{topicId=null, title='Title 2', description='Description 2', author=User{userId=null, name='John', lastName='Doe', email='john.doe@example.com', password='password123', phone='1234567890', birthDate=null, registrationDate=null, gender=MALE, dni='12345678X', profilePicture='null', bio='null', isAdmin=true}, dateTime=2023-05-23T12:30}";
        assertEquals(expected, anotherForumTopic.toString());
    }
}
