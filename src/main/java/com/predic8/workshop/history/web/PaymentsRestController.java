package com.predic8.workshop.history.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.workshop.history.dto.Payment;
import com.predic8.workshop.history.dto.PaymentRequest;
import com.predic8.workshop.history.dto.PaymentSucceeded;
import com.predic8.workshop.history.dto.RatingRequest;
import com.predic8.workshop.history.event.Operation;
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

@RequestMapping("/payments")
@RestController
public class PaymentsRestController {

	private final ObjectMapper mapper;
	private final Map<String, Payment> payments;
	private final RestTemplate rest;
	private final KafkaTemplate<String, Operation> kafka;

	public PaymentsRestController(ObjectMapper objectMapper, Map<String, Payment> payments, RestTemplate restTemplate, KafkaTemplate<String, Operation> kafkaTemplate) {
		this.mapper = objectMapper;
		this.payments = payments;
		this.rest = restTemplate;
		this.kafka = kafkaTemplate;
	}

	@GetMapping("/{uuid}")
	public Payment index(@PathVariable String uuid) {
		return payments.get(uuid);
	}

	@PostMapping("/{uuid}")
	public ResponseEntity<?> save(@PathVariable String uuid, @RequestBody PaymentRequest request) {
		// what happens if the HTTP call succeeds but sending the event does not?
		RatingRequest ratingRequest = new RatingRequest(payments.get(uuid).getCustomer(), payments.get(uuid).getAmount());
		rest.postForEntity("http://rating/ratings", ratingRequest, Void.class);


		Operation op = new Operation("create", "payment", mapper.valueToTree(new PaymentSucceeded(ratingRequest, request)));

		op.logSend();

		kafka.send("shop", op);

		return ResponseEntity.accepted().build();
	}
}