// Movies.java
package com.app.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "movies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"showTimes"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Movies extends BaseEntity {
    
    private String mName;
    private String mDescription;
    private int mRating;
    private String imagePath;
    private String movieImageName;
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false; // Default to false
    
    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
//    @JsonManagedReference // This is the parent in the relationship
//    @JsonIgnore
    private List<ShowtimesEntity> showTimes;
}
