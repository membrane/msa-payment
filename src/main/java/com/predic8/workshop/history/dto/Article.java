package com.predic8.workshop.history.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Article {
	private String article;
	private long quantity;
	private BigDecimal price;
}