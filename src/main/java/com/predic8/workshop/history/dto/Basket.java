package com.predic8.workshop.history.dto;

import lombok.Data;

import java.util.List;

@Data
public class Basket {
	private String uuid;
	private String customer;
	private List<Article> items;
}