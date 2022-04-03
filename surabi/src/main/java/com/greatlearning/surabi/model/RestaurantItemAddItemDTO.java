package com.greatlearning.surabi.model;

import lombok.Data;
import lombok.ToString;


/**
 * @author sriram
 *
 */
@Data
@ToString
public class RestaurantItemAddItemDTO {
	private long itemId;

	public RestaurantItemAddItemDTO(long itemId) {
		this.itemId = itemId;
	}

	public RestaurantItemAddItemDTO() {

	}
}
