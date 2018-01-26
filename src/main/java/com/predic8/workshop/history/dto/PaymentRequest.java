package com.predic8.workshop.history.dto;

import static com.predic8.workshop.history.dto.PaymentRequest.Method.CC;

public class PaymentRequest {
	private Method method = CC;

	public Method getMethod() {
		return this.method;
	}

	public String toString() {
		return "PaymentRequest(method=" + this.getMethod() + ")";
	}

	enum Method {
		CC,
		PAYPAL
	}
}