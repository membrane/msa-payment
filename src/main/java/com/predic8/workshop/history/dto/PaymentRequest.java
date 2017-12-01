package com.predic8.workshop.history.dto;

import lombok.Value;

import static com.predic8.workshop.history.dto.PaymentRequest.Method.CC;

@Value
public class PaymentRequest {
	Method method = CC;

	enum Method {
		CC,
		PAYPAL
	}
}