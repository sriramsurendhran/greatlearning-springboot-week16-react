package com.greatlearning.surabi.service;

import java.time.LocalDate;
import java.util.List;

import com.greatlearning.surabi.entity.RestaurantUser;
import com.greatlearning.surabi.model.RestaurantGeneratedBillsDTO;
import com.greatlearning.surabi.model.RestaurantOrderDetailsDTO;


/**
 * @author sriram
 *
 */
public interface RestaurantUserService {
	public List<RestaurantUser> getAllUsers();

	public String createUser(String role, RestaurantUser restaurantUser);

	public String updateUser(String role, RestaurantUser restaurantUser);

	public String deleteUser(String userName);

	public String registerUser(String userName, String password);

	public String addItemstoOrder(RestaurantOrderDetailsDTO restaurantOrderDetailsDTO);

	public List<RestaurantGeneratedBillsDTO> viewOrder();

	public String confirmOrder();

	public List<RestaurantGeneratedBillsDTO> getGeneratedBills(LocalDate fromDate, LocalDate toDate);
}
