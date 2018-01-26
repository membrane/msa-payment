package com.predic8.workshop.history.dto;

import java.math.BigDecimal;

public class Article {
	private String article;
	private long quantity;
	private BigDecimal price;

	public Article() {
	}

	public String getArticle() {
		return this.article;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public String toString() {
		return "Article(article=" + article + ", quantity=" + quantity + ", price=" + price + ")";
	}
}