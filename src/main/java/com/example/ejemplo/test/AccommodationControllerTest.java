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

import com.example.ejemplo.controller.AccommodationController;
import com.example.ejemplo.dao.AccommodationDAO;
import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.User;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccommodationControllerTest {

    private AccommodationController accommodationController;
    private FakeAccommodationDAO accommodationDAO;

    @Before
    public void setUp() {
        accommodationDAO = new FakeAccommodationDAO();
        accommodationController = new AccommodationController() {
            @Override
            public List<Accommodation> getAllAccommodations() {
                return accommodationDAO.getAll();
            }

            @Override
            public boolean createAccommodation(Accommodation accommodation) {
                return accommodationDAO.create(accommodation);
            }

            @Override
            public boolean updateAccommodation(Accommodation accommodation) {
                return accommodationDAO.update(accommodation);
            }

            @Override
            public boolean deleteAccommodation(Long accommodationId) {
                return accommodationDAO.delete(accommodationId);
            }

            @Override
            public Accommodation getAccommodationById(Long accommodationId) {
                return accommodationDAO.getById(accommodationId);
            }

            @Override
            public List<Accommodation> getAccommodationsByOwner(Long ownerId) {
                return accommodationDAO.getByOwner(ownerId);
            }

            @Override
            public List<AccommodationReview> getReviewsByAccommodation(Long accommodationId) {
                return accommodationDAO.getReviewsByAccommodation(accommodationId);
            }

            @Override
            public List<User> getTenantsForAccommodation(Long accommodationId) {
                return accommodationDAO.getTenantsForAccommodation(accommodationId);
            }
        };
    }

    @Test
    public void getAllAccommodations() {
        List<Accommodation> accommodations = accommodationController.getAllAccommodations();
        assertNotNull(accommodations);
        assertEquals(2, accommodations.size());
    }

    @Test
    public void createAccommodation() {
        Accommodation accommodation = new Accommodation();
        boolean result = accommodationController.createAccommodation(accommodation);
        assertTrue(result);
        assertEquals(3, accommodationDAO.getAll().size());
    }

    @Test
    public void updateAccommodation() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        accommodation.setDescription("Updated Description");
        boolean result = accommodationController.updateAccommodation(accommodation);
        assertTrue(result);
        assertEquals("Updated Description", accommodationDAO.getById(accommodation.getAccommodationId()).getDescription());
    }

    @Test
    public void deleteAccommodation() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        boolean result = accommodationController.deleteAccommodation(accommodation.getAccommodationId());
        assertTrue(result);
        assertEquals(1, accommodationDAO.getAll().size());
    }

    @Test
    public void getAccommodationById() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        Accommodation result = accommodationController.getAccommodationById(accommodation.getAccommodationId());
        assertNotNull(result);
        assertEquals(accommodation.getAccommodationId(), result.getAccommodationId());
    }

    @Test
    public void getAccommodationsByOwner() {
        Long ownerId = 1L;
        List<Accommodation> accommodations = accommodationController.getAccommodationsByOwner(ownerId);
        assertNotNull(accommodations);
        assertEquals(1, accommodations.size());
    }

    @Test
    public void getReviewsByAccommodation() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        List<AccommodationReview> reviews = accommodationController.getReviewsByAccommodation(accommodation.getAccommodationId());
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
    }

    @Test
    public void getTenantsForAccommodation() {
        Accommodation accommodation = accommodationDAO.getAll().get(0);
        List<User> tenants = accommodationController.getTenantsForAccommodation(accommodation.getAccommodationId());
        assertNotNull(tenants);
        assertEquals(1, tenants.size());
    }

    class FakeAccommodationDAO extends AccommodationDAO {
        private List<Accommodation> accommodations = new ArrayList<>();
        private List<AccommodationReview> reviews = new ArrayList<>();

        public FakeAccommodationDAO() {
            User owner = new User();
            owner.setUserId(1L);
            owner.setName("Owner 1");

            Accommodation accommodation1 = new Accommodation();
            accommodation1.setAccommodationId(1L);
            accommodation1.setOwner(owner);
            accommodation1.setDescription("Description 1");
            accommodation1.setPrice(new BigDecimal("100.00"));
            accommodations.add(accommodation1);

            Accommodation accommodation2 = new Accommodation();
            accommodation2.setAccommodationId(2L);
            accommodation2.setOwner(owner);
            accommodation2.setDescription("Description 2");
            accommodation2.setPrice(new BigDecimal("200.00"));
            accommodations.add(accommodation2);

            AccommodationReview review1 = new AccommodationReview();
            review1.setAccommodation(accommodation1);
            review1.setRating(4);

            AccommodationReview review2 = new AccommodationReview();
            review2.setAccommodation(accommodation1);
            review2.setRating(5);

            reviews.add(review1);
            reviews.add(review2);
        }

        @Override
        public List<Accommodation> getAll() {
            return new ArrayList<>(accommodations);
        }

        @Override
        public boolean create(Accommodation accommodation) {
            accommodation.setAccommodationId((long) (accommodations.size() + 1));
            return accommodations.add(accommodation);
        }

        @Override
        public boolean update(Accommodation accommodation) {
            int index = -1;
            for (int i = 0; i < accommodations.size(); i++) {
                if (accommodations.get(i).getAccommodationId().equals(accommodation.getAccommodationId())) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                accommodations.set(index, accommodation);
                return true;
            }
            return false;
        }

        @Override
        public boolean delete(Long accommodationId) {
            return accommodations.removeIf(accommodation -> accommodation.getAccommodationId().equals(accommodationId));
        }

        @Override
        public List<Accommodation> getByOwner(Long ownerId) {
            List<Accommodation> result = new ArrayList<>();
            for (Accommodation accommodation : accommodations) {
                if (accommodation.getOwner().getUserId().equals(ownerId)) {
                    result.add(accommodation);
                }
            }
            return result;
        }

    }
}
