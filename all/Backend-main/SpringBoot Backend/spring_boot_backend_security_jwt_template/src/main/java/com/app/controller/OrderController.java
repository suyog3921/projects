package com.app.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
	@Value("${razorpay.key.id}")
	private String razorpayId ;
	@Value("${razorpay.key.secret}")
	private String razorpaySecret;
	@ResponseBody
	@PostMapping
	public String createOrder(@RequestBody Map<String, Object> data) {
			System.out.println(data);
			int amount = Integer.parseInt(data.get("amount").toString());
			System.out.println(amount);
			Order order = null;
			try {
			RazorpayClient razorpay = new RazorpayClient(razorpayId, razorpaySecret);
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount",amount*100);
			orderRequest.put("currency","INR");
			orderRequest.put("receipt", "receipt#1");
			JSONObject notes = new JSONObject();
			notes.put("notes_key_1","Tea, Earl Grey, Hot");
			orderRequest.put("notes",notes);		
				order = razorpay.orders.create(orderRequest);
			} catch (RazorpayException e) {
				e.printStackTrace();
			}
			System.out.println("here");
			return order.toString();
	}
}
