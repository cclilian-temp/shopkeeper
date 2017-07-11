package com.mall.shopkeeper.iface.aspect;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 会话和权限校验相关逻辑
 *
 * >> 校验会话状态
 * 
 * @author fyl119037
 */
@Aspect
@Order(30)
@Component
public class SessionAspect {

	
}
