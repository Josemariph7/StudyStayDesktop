package com.example.ejemplo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostTest {

    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post(1L, null, null, "Title", "Content", LocalDateTime.now());
    }

    @Test
    public void testPostConstructor() {
        assertNotNull(post);
    }

    @Test
    public void testPostAttributes() {
        assertEquals(1L, post.getPostId());
        assertEquals("Title", post.getTitle());
        assertEquals("Content", post.getContent());
        assertNotNull(post.getDateTime());
    }

    @Test
    public void testEqualsAndHashCode() {
        Post samePost = new Post(1L, null, null, "Title", "Content", LocalDateTime.now());
        Post differentPost = new Post(2L, null, null, "Different Title", "Different Content", LocalDateTime.now());

        assertEquals(post, samePost);
        assertEquals(post.hashCode(), samePost.hashCode());
        assertNotEquals(post, differentPost);
        assertNotEquals(post.hashCode(), differentPost.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Post{" +
                "postId=1, " +
                "forum=null, " +
                "author=null, " +
                "title='Title', " +
                "content='Content', " +
                "dateTime=" + post.getDateTime() +
                "}";
        assertEquals(expectedString, post.toString());
    }
}
