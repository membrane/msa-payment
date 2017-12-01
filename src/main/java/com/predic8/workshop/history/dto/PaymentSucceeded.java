package com.predic8.workshop.history.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PaymentSucceeded {
	@JsonUnwrapped
	RatingRequest ratingRequest;
	@JsonUnwrapped
	PaymentRequest paymentRequest;

	public PaymentSucceeded(RatingRequest ratingRequest, PaymentRequest paymentRequest) {
		this.ratingRequest = ratingRequest;
		this.paymentRequest = paymentRequest;
	}

	public RatingRequest getRatingRequest() {
		return this.ratingRequest;
	}

	public PaymentRequest getPaymentRequest() {
		return this.paymentRequest;
	}

	public String toString() {
		return "PaymentSucceeded(ratingRequest=" + ratingRequest + ", paymentRequest=" + paymentRequest + ")";
	}
}
