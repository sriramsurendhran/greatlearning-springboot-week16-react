package com.greatlearning.surabi.model;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * @author sriram
 *
 */
@Getter
@EqualsAndHashCode
public class RestaurantOrderDetailsDTO {
	private List<RestaurantItemAddItemDTO> restaurantItemAddItemDTO;

	public RestaurantOrderDetailsDTO(List<RestaurantItemAddItemDTO> restaurantItemAddItemDTO) {
		this.restaurantItemAddItemDTO = restaurantItemAddItemDTO;
	}

	public RestaurantOrderDetailsDTO() {
		super();
	}

}
