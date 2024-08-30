// ShowtimesEntity.java
package com.app.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.app.service.LocalTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "showtimes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"movie", "showTimes"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ShowtimesEntity extends BaseEntity {
//	@JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime showStartTime;
//	@JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime showEndTime;
    private LocalDate showDate;
    
    @ManyToOne
    @JoinColumn(name = "movie_id")  // This is the field that the mappedBy attribute in Movies refers to
//    @JsonBackReference
    private Movies movie;
    
    @OneToMany(mappedBy = "showtime", fetch = FetchType.EAGER)
    private Set<SeatEntity> seats=new HashSet<>();
    public String getFormattedShowtime() {
        return String.format("%s, %s - %s", 
                             showDate.toString(), 
                             showStartTime.toString(), 
                             showEndTime.toString());
    }

}
