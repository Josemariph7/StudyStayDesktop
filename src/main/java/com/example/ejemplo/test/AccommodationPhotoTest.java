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
import com.example.ejemplo.model.AccommodationPhoto;
import org.junit.*;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccommodationPhotoTest {

    private AccommodationPhoto accommodationPhoto;
    private AccommodationPhoto anotherAccommodationPhoto;

    @Before
    public void setUp() {
        Accommodation accommodation = new Accommodation();
        byte[] photoData = {1, 2, 3, 4, 5};

        accommodationPhoto = new AccommodationPhoto(accommodation, photoData);
        anotherAccommodationPhoto = new AccommodationPhoto(accommodation, photoData);
    }

    @Test
    public void testGetAndSetPhotoId() {
        accommodationPhoto.setPhotoId(1L);
        assertEquals(Optional.of(1L), Optional.ofNullable(accommodationPhoto.getPhotoId()));
    }

    @Test
    public void testGetAndSetAccommodation() {
        Accommodation accommodation = new Accommodation();
        accommodationPhoto.setAccommodation(accommodation);
        assertEquals(accommodation, accommodationPhoto.getAccommodation());
    }

    @Test
    public void testGetAndSetPhotoData() {
        byte[] photoData = {1, 2, 3, 4, 5};
        accommodationPhoto.setPhotoData(photoData);
        assertArrayEquals(photoData, accommodationPhoto.getPhotoData());
    }


    @Test
    public void testToString() {
        String expected = "AccommodationPhoto{photoId=null, accommodation=" + accommodationPhoto.getAccommodation() + "}";
        assertEquals(expected, accommodationPhoto.toString());
    }
}
