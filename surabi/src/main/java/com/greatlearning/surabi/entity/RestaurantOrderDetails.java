package com.greatlearning.surabi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author sriram
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RestaurantOrderDetails {
	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;

	@Column(name = "ORDER_STATUS")
	private String orderStatus;

	@Column(name = "ORDER_BILL_AMOUNT")
	private double orderBillAmount;

	@Column(name = "ORDER_USER_NAME")
	private String orderUserName;

	@Column(name = "ORDER_DATE")
	@UpdateTimestamp
	private LocalDateTime orderDate;

	@OneToMany(mappedBy = "restaurantOrderDetails", cascade = CascadeType.ALL)
	private List<RestaurantItemOrderDetails> restaurantItemOrderDetails;

}
