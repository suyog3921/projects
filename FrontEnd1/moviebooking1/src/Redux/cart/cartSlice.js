// src/Redux/cart/cartSlice.js
import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  value: 0, // Total amount
  selectedSeats: 0, // Number of selected seats
};

const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    increment: (state) => {
      state.selectedSeats += 1;
      state.value += 120; // Cost per seat
    },
    decrement: (state) => {
      if (state.selectedSeats > 0) {
        state.selectedSeats -= 1;
        state.value -= 120; // Cost per seat
      }
    },
    reset: (state) => {
      state.value = 0;
      state.selectedSeats = 0;
    },
  },
});

export const { increment, decrement, reset } = cartSlice.actions;

export default cartSlice.reducer;
