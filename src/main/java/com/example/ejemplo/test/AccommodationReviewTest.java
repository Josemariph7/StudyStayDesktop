package com.example.ejemplo.test;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.User;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccommodationReviewTest {

    private AccommodationReview review;
    private AccommodationReview anotherReview;

    @Before
    public void setUp() {
        User author = new User("John", "Doe", "john.doe@example.com", "password123", "1234567890",
                java.time.LocalDate.of(1990, 1, 1), User.Gender.MALE, "12345678X", true);
        Accommodation accommodation = new Accommodation();
        LocalDateTime dateTime = LocalDateTime.of(2023, 5, 1, 14, 0);

        review = new AccommodationReview(accommodation, author, 4.5, "Great place!", dateTime);
        anotherReview = new AccommodationReview(accommodation, author, 4.0, "Nice place.", dateTime);
    }

    @Test
    public void testGetAndSetReviewId() {
        review.setReviewId(1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(review.getReviewId()));
    }

    @Test
    public void testGetAndSetAccommodation() {
        Accommodation accommodation = new Accommodation();
        review.setAccommodation(accommodation);
        assertEquals(accommodation, review.getAccommodation());
    }

    @Test
    public void testGetAndSetAuthor() {
        User author = new User();
        review.setAuthor(author);
        assertEquals(author, review.getAuthor());
    }

    @Test
    public void testGetAndSetRating() {
        review.setRating(5.0);
        assertEquals(5.0, review.getRating(), 0.01);
    }

    @Test
    public void testGetAndSetComment() {
        review.setComment("Amazing stay!");
        assertEquals("Amazing stay!", review.getComment());
    }

    @Test
    public void testGetAndSetDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 6, 1, 14, 0);
        review.setDateTime(dateTime);
        assertEquals(dateTime, review.getDateTime());
    }

    @Test
    public void testEquals() {
        AccommodationReview anotherReviewCopy = new AccommodationReview(review.getAccommodation(), review.getAuthor(),
                review.getRating(), review.getComment(), review.getDateTime());
        assertEquals(review, anotherReviewCopy);
        assertNotEquals(review, anotherReview);
    }

    @Test
    public void testHashCode() {
        AccommodationReview anotherReviewCopy = new AccommodationReview(review.getAccommodation(), review.getAuthor(),
                review.getRating(), review.getComment(), review.getDateTime());
        assertEquals(review.hashCode(), anotherReviewCopy.hashCode());
        assertNotEquals(review.hashCode(), anotherReview.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "AccommodationReview{reviewId=null, accommodation=" + review.getAccommodation() +
                ", author=" + review.getAuthor() +
                ", rating=4.5, comment='Great place!', dateTime=" + review.getDateTime() + "}";
        assertEquals(expected, review.toString());
    }
}
