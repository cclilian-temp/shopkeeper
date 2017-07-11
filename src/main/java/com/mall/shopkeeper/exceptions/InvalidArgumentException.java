package com.mall.shopkeeper.exceptions;

@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException {

	public InvalidArgumentException(Exception e) {
		super(e);
	}
	
	public InvalidArgumentException(String s) {
		super(s);
	}
}
