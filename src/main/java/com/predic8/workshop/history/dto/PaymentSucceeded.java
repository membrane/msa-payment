package com.predic8.workshop.history.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

@Value
public class PaymentSucceeded {
	@JsonUnwrapped
	RatingRequest ratingRequest;
	@JsonUnwrapped
	PaymentRequest paymentRequest;
}
