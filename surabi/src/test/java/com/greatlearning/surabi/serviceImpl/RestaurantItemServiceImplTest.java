package com.greatlearning.surabi.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.greatlearning.surabi.entity.RestaurantItem;
import com.greatlearning.surabi.repository.RestaurantItemRepository;

@SpringBootTest
public class RestaurantItemServiceImplTest {

	@Mock
	private RestaurantItemRepository restaurantItemRepository;

	@InjectMocks
	private RestaurantItemServiceImpl restaurantItemServiceImpl;

	@Test
	public void shouldReturnAllItems() {
		RestaurantItem restaurantItem = new RestaurantItem(3, "Veg soup", 40);
		List<RestaurantItem> restaurantItemList = new ArrayList<>();
		restaurantItemList.add(restaurantItem);

		Mockito.when(restaurantItemRepository.findAll()).thenReturn(restaurantItemList);

		List<RestaurantItem> fetchedrestaurantItemList = restaurantItemServiceImpl.getAllItems();

		Assertions.assertEquals(restaurantItemList, fetchedrestaurantItemList);
	}
}
