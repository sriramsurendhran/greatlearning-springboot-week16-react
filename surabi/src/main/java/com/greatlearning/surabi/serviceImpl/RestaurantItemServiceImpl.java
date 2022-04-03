package com.greatlearning.surabi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greatlearning.surabi.entity.RestaurantItem;
import com.greatlearning.surabi.repository.RestaurantItemRepository;
import com.greatlearning.surabi.service.RestaurantItemService;


/**
 * @author sriram
 *
 */
@Service
public class RestaurantItemServiceImpl implements RestaurantItemService {

	@Autowired
	RestaurantItemRepository restaurantItemRepository;

	public List<RestaurantItem> getAllItems() {
		return restaurantItemRepository.findAll();
	}

}
