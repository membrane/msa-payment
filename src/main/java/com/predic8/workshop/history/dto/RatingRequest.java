package com.predic8.workshop.history.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class RatingRequest {
	String customer;
	BigDecimal amount;
}