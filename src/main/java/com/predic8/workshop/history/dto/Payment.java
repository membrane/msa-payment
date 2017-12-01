package com.predic8.workshop.history.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class Payment {
	@JsonIgnore
	private String customer;
	private BigDecimal amount;

	public Payment() {
	}

	public Payment(String customer, BigDecimal amount) {
		this.customer = customer;
		this.amount = amount;
	}

	public String getCustomer() {
		return this.customer;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public String toString() {
		return "Payment(customer=" + customer + ", amount=" + amount + ")";
	}
}