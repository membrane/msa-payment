package com.predic8.workshop.history.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.history.dto.Basket;
import com.predic8.workshop.history.dto.Payment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class ShopListener {

	private final ObjectMapper mapper;
	private final Map<String, Payment> payments;

	public ShopListener(ObjectMapper objectMapper, Map<String, Payment> payments) {
		this.mapper = objectMapper;
		this.payments = payments;
	}

	@KafkaListener(topics = "shop")
	public void listen(Operation op) {

		if (!op.getBo().equals("basket")) {
			return;
		}

		op.logReceive();

		Basket basket = mapper.convertValue(op.getObject(), Basket.class);
		payments.put(basket.getUuid(), toPayment(basket));
	}

	private static Payment toPayment(Basket basket) {
		return new Payment(
			basket.getCustomer(),
			basket.getItems()
			      .stream()
			      .map(i -> i.getPrice().multiply(new BigDecimal(i.getQuantity())))
			      .reduce(BigDecimal.ZERO, BigDecimal::add)
		);
	}
}