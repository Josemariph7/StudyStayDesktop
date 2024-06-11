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
