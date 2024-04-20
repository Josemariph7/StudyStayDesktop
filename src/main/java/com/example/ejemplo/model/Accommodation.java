package com.example.ejemplo.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un alojamiento en el sistema.
 */
public class Accommodation {
    private Long accommodationId; // Identificador único del alojamiento
    private User owner; // Propietario del alojamiento
    private String address; // Dirección del alojamiento
    private String city; // Ciudad del alojamiento
    private BigDecimal price; // Precio del alojamiento por noche
    private String description; // Descripción del alojamiento
    private int capacity; // Capacidad máxima de huéspedes del alojamiento
    private String services; // Servicios ofrecidos por el alojamiento
    private boolean availability; // Disponibilidad actual del alojamiento
    private double rating; // Calificación promedio del alojamiento
    private List<Booking> bookings; // Lista de reservas realizadas para este alojamiento
    private List<AccommodationReview> reviews; // Lista de reseñas realizadas para este alojamiento

    // Constructor

    /**
     * Constructor de la clase Accommodation.
     *
     * @param owner       Propietario del alojamiento.
     * @param address     Dirección del alojamiento.
     * @param city        Ciudad del alojamiento.
     * @param price       Precio del alojamiento por noche.
     * @param description Descripción del alojamiento.
     * @param capacity    Capacidad máxima de huéspedes del alojamiento.
     * @param services    Servicios ofrecidos por el alojamiento.
     */
    public Accommodation(User owner, String address, String city, BigDecimal price, String description, int capacity, String services) {
        this.owner = owner;
        this.address = address;
        this.city = city;
        this.price = price;
        this.description = description;
        this.capacity = capacity;
        this.services = services;
        this.availability = true; // Por defecto, el alojamiento está disponible
        this.rating = 0; // Por defecto, el alojamiento no tiene calificación
    }

    // Getters y setters

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<AccommodationReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<AccommodationReview> reviews) {
        this.reviews = reviews;
    }

    // equals, hashCode y toString

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return Objects.equals(accommodationId, that.accommodationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accommodationId);
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "accommodationId=" + accommodationId +
                ", owner=" + owner +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", services='" + services + '\'' +
                ", availability=" + availability +
                ", rating=" + rating +
                '}';
    }
}
