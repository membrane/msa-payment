package com.predic8.workshop.history.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.history.dto.Payment;
import com.predic8.workshop.history.dto.PaymentRequest;
import com.predic8.workshop.history.dto.PaymentSucceeded;
import com.predic8.workshop.history.dto.RatingRequest;
import com.predic8.workshop.history.event.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/payments")
@RestController
public class PaymentsRestController {
	private final ObjectMapper objectMapper;
	private final Map<String, Payment> payments;
	private final RestTemplate restTemplate;
	private final KafkaTemplate<String, Operation> kafkaTemplate;

	@GetMapping("/{uuid}")
	public Payment index(@PathVariable String uuid) {
		return payments.get(uuid);
	}

	@PostMapping("/{uuid}")
	public ResponseEntity<?> save(@PathVariable String uuid, @RequestBody PaymentRequest paymentRequest) {
		// what happens if the HTTP call succeeds but sending the event does not?
		RatingRequest ratingRequest = new RatingRequest(payments.get(uuid).getCustomer(), payments.get(uuid).getAmount());
		restTemplate.postForEntity("http://rating-service/ratings", ratingRequest, Void.class);

		kafkaTemplate.send("shop", new Operation("create", "payment", objectMapper.valueToTree(new PaymentSucceeded(ratingRequest, paymentRequest))));

		return ResponseEntity.accepted().build();
	}
}