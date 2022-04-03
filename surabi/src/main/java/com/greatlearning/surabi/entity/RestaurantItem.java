package com.greatlearning.surabi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@NoArgsConstructor
@Entity
public class RestaurantItem {

	@Id
	@Column(name = "ITEM_ID")
	private long itemId;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "ITEM_PRICE")
	private double price;

	public RestaurantItem(long itemId, String itemName, double price) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
	}

}
