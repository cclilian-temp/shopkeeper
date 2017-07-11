package com.mall.shopkeeper.exceptions;

/**
 * 用于将Throwable包装为RuntimeException
 * 
 * @author fyl119037
 * 
 */
@SuppressWarnings("serial")
public class ExceptionWrapper extends RuntimeException {

	public ExceptionWrapper(Throwable e) {
		super(e);
	}

}
