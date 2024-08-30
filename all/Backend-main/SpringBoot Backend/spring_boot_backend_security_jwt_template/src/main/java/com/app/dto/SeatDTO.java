package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SeatDTO {
    private Long id;            // Optional: if you need the ID
    private int seatNo;
    private Boolean isSeatAvailable;
}
