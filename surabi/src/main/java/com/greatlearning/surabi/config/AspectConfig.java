package com.greatlearning.surabi.config;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.greatlearning.surabi.entity.AuditLog;
import com.greatlearning.surabi.repository.AuditRepository;
import com.greatlearning.surabi.service.RestaurantItemService;
import com.greatlearning.surabi.service.RestaurantUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author sriram
 * Aspect Config class for implementing cross cutting concerns - audit log
 */
@Configuration
@Aspect
@Slf4j
public class AspectConfig {

	@Autowired
	AuditRepository auditRepository;

	@Autowired
	RestaurantUserService restaurantUserService;

	@Autowired
	RestaurantItemService restaurantItemService;

	@Before("execution(public * com.greatlearning.surabi.serviceImpl.*.*(..))")
	public void logBeforeAllMethods(JoinPoint joinPoint) {
		log.info(joinPoint.getSignature().getName() + "Started");
	}

	@After("execution(public * com.greatlearning.surabi.serviceImpl.*.*(..))")
	public void logAfterAllMethods(JoinPoint joinPoint) {
		log.info(joinPoint.getSignature().getName() + "Ended");
	}

	@AfterReturning("execution(public * com.greatlearning.surabi.serviceImpl.RestaurantUserServiceImpl.createUser(..))")
	public void logBeforeCreateUser(JoinPoint joinPoint) {
		auditRepository.saveAndFlush(AuditLog.builder().createDate(new Date())
				.description("New user created " + joinPoint.getArgs()[0]).build());
	}

	@AfterReturning("execution(public * com.greatlearning.surabi.serviceImpl.RestaurantUserServiceImpl.updateUser(..))")
	public void logBeforeUpdateUser(JoinPoint joinPoint) {
		auditRepository.saveAndFlush(AuditLog.builder().createDate(new Date())
				.description("User Updated  " + joinPoint.getArgs()[0]).build());
	}

	@AfterReturning("execution(public * com.greatlearning.surabi.serviceImpl.RestaurantUserServiceImpl.deleteUser(..))")
	public void logBeforeDeleteUser(JoinPoint joinPoint) {
		auditRepository.saveAndFlush(AuditLog.builder().createDate(new Date())
				.description("User Deleted  " + joinPoint.getArgs()[0]).build());
	}

	@AfterReturning(value = "execution(public * com.greatlearning.surabi.serviceImpl.RestaurantUserServiceImpl.confirmOrder(..))", returning = "returned")
	public void logBeforeConfirmOrder(JoinPoint joinPoint, String returned) {
		auditRepository.saveAndFlush(AuditLog.builder().createDate(new Date())
				.description("Order Confirmed and Bill Generated " + returned).build());
	}

	@AfterReturning(value = "execution(public * com.greatlearning.surabi.serviceImpl.RestaurantUserServiceImpl.addItemstoOrder(..))", returning = "returned")
	public void logBeforeAddItemstoOrder(JoinPoint joinPoint, String returned) {
		auditRepository.saveAndFlush(
				AuditLog.builder().createDate(new Date()).description("Items added to Order  " + returned).build());
	}

	@AfterThrowing(value = "execution(public * com.greatlearning.surabi.serviceImpl.*.*(..))", throwing = "ex")
	public void LogIfErrorWhileCreateUser(JoinPoint joinPoint, Exception ex) {
		auditRepository
				.saveAndFlush(AuditLog
						.builder().createDate(new Date()).description("Error while "
								+ ex.getStackTrace()[0].getMethodName() + " - Error message : " + ex.getMessage())
						.build());
	}

}
