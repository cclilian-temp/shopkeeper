package com.mall.shopkeeper.exceptions;

@SuppressWarnings("serial")
public class JsonProcessException extends RuntimeException {

	public JsonProcessException(Exception e) {
		super(e);
	}
}
