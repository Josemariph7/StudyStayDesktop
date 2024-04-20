package com.example.ejemplo.test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentTest {

    private Comment comment;

    @BeforeEach
    public void setUp() {
        comment = new Comment(1L, null, null, "Test content", LocalDateTime.now());
    }

    @Test
    public void testCommentConstructor() {
        assertNotNull(comment);
    }

    @Test
    public void testCommentAttributes() {
        assertEquals(1L, comment.getCommentId());
        assertEquals("Test content", comment.getContent());
        assertNotNull(comment.getDateTime());
    }

    @Test
    public void testSettersAndGetters() {
        LocalDateTime newDateTime = LocalDateTime.now().minusDays(1);
        comment.setDateTime(newDateTime);
        assertEquals(newDateTime, comment.getDateTime());

        String newContent = "New content";
        comment.setContent(newContent);
        assertEquals(newContent, comment.getContent());
    }

    @Test
    public void testEqualsAndHashCode() {
        Comment sameComment = new Comment(1L, null, null, "Test content", LocalDateTime.now());
        Comment differentComment = new Comment(2L, null, null, "Different content", LocalDateTime.now());

        assertEquals(comment, sameComment);
        assertEquals(comment.hashCode(), sameComment.hashCode());
        assertNotEquals(comment, differentComment);
        assertNotEquals(comment.hashCode(), differentComment.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Comment{" +
                "commentId=1, " +
                "post=null, " +
                "author=null, " +
                "content='Test content', " +
                "dateTime=" + comment.getDateTime() +
                "}";
        assertEquals(expectedString, comment.toString());
    }
}
