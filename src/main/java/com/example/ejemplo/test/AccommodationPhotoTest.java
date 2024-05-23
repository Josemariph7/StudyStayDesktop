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
