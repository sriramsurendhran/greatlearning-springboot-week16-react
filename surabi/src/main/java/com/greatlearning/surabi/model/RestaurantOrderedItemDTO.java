package com.greatlearning.surabi.model;

import lombok.Getter;


/**
 * @author sriram
 *
 */
@Getter
public class RestaurantOrderedItemDTO {
	private long itemId;
	private String itemName;
	private double price;

	public RestaurantOrderedItemDTO(long itemId, String itemName, double price) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
	}

}
