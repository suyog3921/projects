// SeatEntity.java
package com.app.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeatEntity extends BaseEntity {

    private int seatNo;
    
    private Boolean isSeatAvailable;
    
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private ShowtimesEntity showtime;
}
