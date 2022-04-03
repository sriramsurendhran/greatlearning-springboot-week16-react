package com.greatlearning.surabi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * @author sriram
 *
 */
@Getter
@Setter
@Data
public class RestaurantGeneratedBillsDTO {
	private long orderId;

	private String orderStatus;

	private double orderBillAmount;

	private String orderUserName;

	private List<RestaurantOrderedItemDTO> restaurantOrderedItemDTO = new ArrayList<>();
}