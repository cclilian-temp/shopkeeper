package com.mall.shopkeeper.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 此注解用于方法，标记该方法允许访问的角色
 * 
 * @author fyl119037
 *
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckRoles {
	String[] value();
}
