package com.example.ejemplo.test;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReviewTest {

    private Review review;
    private Accommodation accommodation;
    private User author;

    @BeforeEach
    public void setUp() {
        accommodation = new Accommodation();
        author = new User();
        review = new Review(1L, accommodation, author, 5, "Great place!", LocalDateTime.now());
    }

    @Test
    public void testReviewConstructor() {
        assertNotNull(review);
    }

    @Test
    public void testReviewAttributes() {
        assertEquals(1L, review.getReviewId());
        assertEquals(accommodation, review.getAccommodation());
        assertEquals(author, review.getAuthor());
        assertEquals(5, review.getRating());
        assertEquals("Great place!", review.getComment());
        assertNotNull(review.getDateTime());
    }

    @Test
    public void testEqualsAndHashCode() {
        Review sameReview = new Review(1L, accommodation, author, 5, "Great place!", LocalDateTime.now());
        Review differentReview = new Review(2L, accommodation, author, 4, "Not so great", LocalDateTime.now());

        assertEquals(review, sameReview);
        assertEquals(review.hashCode(), sameReview.hashCode());
        assertNotEquals(review, differentReview);
        assertNotEquals(review.hashCode(), differentReview.hashCode());
    }

    @Test
    public void testToString() {
        String expectedString = "Review{" +
                "reviewId=1, " +
                "accommodation=" + accommodation + ", " +
                "author=" + author + ", " +
                "rating=5, " +
                "comment='Great place!', " +
                "dateTime=" + review.getDateTime() +
                "}";
        assertEquals(expectedString, review.toString());
    }
}
