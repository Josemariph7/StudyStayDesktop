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

import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import org.junit.*;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ForumCommentTest {

    private ForumComment forumComment;
    private User author;
    private ForumTopic topic;
    private LocalDateTime dateTime;

    @Before
    public void setUp() {
        author = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890", LocalDateTime.now().toLocalDate(), User.Gender.MALE, "12345678X", true);
        topic = new ForumTopic("Test Topic", "Test Description", author, LocalDateTime.now());
        dateTime = LocalDateTime.now();
        forumComment = new ForumComment(topic, author, "This is a comment", dateTime);
    }

    @Test
    public void testGetAndSetCommentId() {
        forumComment.setCommentId(1L);
        assertEquals(Long.valueOf(1L), forumComment.getCommentId());
    }

    @Test
    public void testGetAndSetTopic() {
        ForumTopic newTopic = new ForumTopic("New Topic", "New Description", author, LocalDateTime.now());
        forumComment.setTopic(newTopic);
        assertEquals(newTopic, forumComment.getTopic());
    }

    @Test
    public void testGetAndSetAuthor() {
        User newAuthor = new User("Jane", "Doe", "jane.doe@example.com", "password123", "0987654321", LocalDateTime.now().toLocalDate(), User.Gender.FEMALE, "87654321X", false);
        forumComment.setAuthor(newAuthor);
        assertEquals(newAuthor, forumComment.getAuthor());
    }

    @Test
    public void testGetAndSetContent() {
        forumComment.setContent("Updated Comment");
        assertEquals("Updated Comment", forumComment.getContent());
    }

    @Test
    public void testGetAndSetDateTime() {
        LocalDateTime newDateTime = LocalDateTime.now().plusDays(1);
        forumComment.setDateTime(newDateTime);
        assertEquals(newDateTime, forumComment.getDateTime());
    }

    @Test
    public void testEquals() {
        ForumComment anotherComment = new ForumComment(topic, author, "This is a comment", dateTime);
        assertEquals(forumComment, anotherComment);

        anotherComment.setCommentId(1L);
        assertNotEquals(forumComment, anotherComment);
    }

    @Test
    public void testHashCode() {
        ForumComment anotherComment = new ForumComment(topic, author, "This is a comment", dateTime);
        assertEquals(forumComment.hashCode(), anotherComment.hashCode());

        anotherComment.setCommentId(1L);
        assertNotEquals(forumComment.hashCode(), anotherComment.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "ForumComment{commentId=null, topic=" + topic + ", author=" + author + ", content='This is a comment', dateTime=" + dateTime + "}";
        assertEquals(expected, forumComment.toString());
    }
}
