package com.predic8.workshop.history.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Payment {
	@JsonIgnore
	private String customer;
	private BigDecimal amount;
}