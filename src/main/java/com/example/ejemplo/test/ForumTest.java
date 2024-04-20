package com.example.ejemplo.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ForumTest {

    private Forum forum;

    @BeforeEach
    public void setUp() {
        forum = new Forum(1L, "Test Forum");
    }

    @Test
    public void testForumConstructor() {
        assertNotNull(forum);
    }

    @Test
    public void testForumAttributes() {
        assertEquals(1L, forum.getForumId());
        assertEquals("Test Forum", forum.getName());
        assertNotNull(forum.getPosts());
        assertEquals(new ArrayList<Post>(), forum.getPosts());
    }

    @Test
    public void testAddAndRemovePost() {
        Post post1 = new Post(1L, null, null, "Title 1", "Content 1", null);
        Post post2 = new Post(2L, null, null, "Title 2", "Content 2", null);

        forum.addPost(post1);
        forum.addPost(post2);

        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(post1);
        expectedPosts.add(post2);

        assertEquals(expectedPosts, forum.getPosts());

        forum.removePost(post1);
        expectedPosts.remove(post1);

        assertEquals(expectedPosts, forum.getPosts());
    }

    @Test
    public void testEqualsAndHashCode() {
        Forum sameForum = new Forum(1L, "Test Forum");
        Forum differentForum = new Forum(2L, "Different Forum");

        assertEquals(forum, sameForum);
        assertEquals(forum.hashCode(), sameForum.hashCode());
        assertNotEquals(forum, differentForum);
        assertNotEquals(forum.hashCode(), differentForum.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Forum{" +
                "forumId=1, " +
                "name='Test Forum', " +
                "posts=[]" +
                "}";
        assertEquals(expectedString, forum.toString());
    }
}
