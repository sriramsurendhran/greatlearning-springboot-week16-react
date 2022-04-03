package com.greatlearning.surabi.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.surabi.entity.RestaurantOrderDetails;


/**
 * @author sriram
 *
 */
@Repository
public interface RestaurantOrderDetailsRepository extends JpaRepository<RestaurantOrderDetails, Long> {

	RestaurantOrderDetails findByOrderStatusAndOrderUserName(String orderStatus, String orderUserName);

	List<RestaurantOrderDetails> findAllByOrderStatusAndOrderUserName(String orderStatus, String orderUserName);

	boolean existsByOrderStatusAndOrderUserName(String orderStatus, String orderUserName);

	List<RestaurantOrderDetails> findAllByOrderDateBetween(LocalDateTime orderDateStart, LocalDateTime orderDateEnd);

	List<RestaurantOrderDetails> findAllByOrderDateBetweenAndOrderStatus(LocalDateTime orderDateStart,
			LocalDateTime orderDateEnd, String orderStatus);

}
