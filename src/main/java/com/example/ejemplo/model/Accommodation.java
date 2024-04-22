package com.example.ejemplo.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Accommodation {
    private Long accommodationId;
    private User owner;
    private String address;
    private String city;
    private BigDecimal price;
    private String description;
    private int capacity;
    private String services;
    private boolean availability;
    private double rating;
    private List<Booking> bookings;
    private List<AccommodationReview> reviews;
    private List<User> tenants; // Lista de inquilinos

    public Accommodation() {
        tenants = new ArrayList<>();
    }

    public Accommodation(User owner, String address, String city, BigDecimal price, String description, int capacity, String services) {
        this.owner = owner;
        this.address = address;
        this.city = city;
        this.price = price;
        this.description = description;
        this.capacity = capacity;
        this.services = services;
        this.availability = true;
        this.rating = 0;
        tenants = new ArrayList<>();
    }

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

    public List<User> getTenants() {
        return tenants;
    }

    public void setTenants(List<User> tenants) {
        this.tenants = tenants;
    }

    // Método para agregar inquilino
    public boolean addTenant(User tenant) {
        if (tenants.size() < capacity) {
            tenants.add(tenant);
            return true; // Inquilino agregado exitosamente
        } else {
            return false; // No hay espacio disponible para agregar más inquilinos
        }
    }

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
