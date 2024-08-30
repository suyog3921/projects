// Booking.java
package com.app.entities;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private ShowtimesEntity showtime;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    private int totalPrice;
    // Other fields and relationships
    
    private long transactionId;
}
