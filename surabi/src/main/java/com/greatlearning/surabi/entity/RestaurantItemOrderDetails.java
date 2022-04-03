package com.greatlearning.surabi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RestaurantItemOrderDetails {

	@Id
	@Column(name = "ITOR_ID")
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long itorId;

	@JoinColumn(name = "ITOR_ORDER_ID")
	@ManyToOne
	@JsonIgnore
	private RestaurantOrderDetails restaurantOrderDetails;

	@JoinColumn(name = "ITOR_ITEM_ID")
	@ManyToOne
	@JsonIgnore
	private RestaurantItem restaurantItem;

	@Column(name = "ITOR_PRICE")
	private double price;

}
