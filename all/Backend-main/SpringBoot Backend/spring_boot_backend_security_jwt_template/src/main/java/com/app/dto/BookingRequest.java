package com.app.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BookingRequest {
	private Long userId;
	private List<Long> seatId;
	private Long movieId;
	private Long showtimeId;
	private int totalPrice;	    
//	private long transactionId;
}
