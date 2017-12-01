package com.predic8.workshop.history.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.history.dto.Article;
import com.predic8.workshop.history.dto.Basket;
import com.predic8.workshop.history.dto.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopListener {
	private final ObjectMapper objectMapper;
	private final Map<String, Payment> payments;

	@KafkaListener(topics = "shop")
	public void listen(Operation operation) {
		if (!operation.getType().equals("basket")) {
			log.info("Unknown type: {}", operation.getType());

			return;
		}

		Basket basket = objectMapper.convertValue(operation.getObject(), Basket.class);
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