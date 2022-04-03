package com.greatlearning.surabi.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greatlearning.surabi.entity.RestaurantItem;
import com.greatlearning.surabi.entity.RestaurantUser;
import com.greatlearning.surabi.model.RestaurantGeneratedBillsDTO;
import com.greatlearning.surabi.model.RestaurantOrderDetailsDTO;
import com.greatlearning.surabi.service.RestaurantItemService;
import com.greatlearning.surabi.service.RestaurantUserService;
import com.greatlearning.surabi.serviceImpl.RestaurantUserServiceImpl;

import lombok.extern.slf4j.Slf4j;


/**
 * @author sriram
 *RestaurantController - entry point for all API
 */
@RestController
@RequestMapping("/surabi")
@Slf4j
public class RestaurantController {

	@Autowired
	RestaurantItemService restaurantItemService;

	@Autowired
	RestaurantUserService restaurantUserService;

	@GetMapping("/info")
	public String get() {
		return "Welcome to Surabi Restaurants";

	}

	@GetMapping("/menu")
	public List<RestaurantItem> getAllItems() {
		return restaurantItemService.getAllItems();
	}

	@GetMapping("/listusers")
	public List<RestaurantUser> getAllUsers() {
		return restaurantUserService.getAllUsers();
	}

	@PostMapping("/createuser")
	public String createUser(String role, RestaurantUser restaurantUser) {
		return restaurantUserService.createUser(role, restaurantUser);
	}

	@PostMapping("/registeruser")
	public String registerUser(String userName, String password) {
		return restaurantUserService.registerUser(userName, password);
	}

	@PutMapping("/updateuser")
	public String updateUser(String role, RestaurantUser restaurantUser) {
		return restaurantUserService.updateUser(role, restaurantUser);
	}

	@PostMapping("/deleteuser")
	public String deleteUser(String userName) {
		return restaurantUserService.deleteUser(userName);
	}

	@PostMapping("/addItemstoOrder")
	public String addItems(@RequestBody RestaurantOrderDetailsDTO restaurantOrderDetailsDTO) {
		return restaurantUserService.addItemstoOrder(restaurantOrderDetailsDTO);
	}

	@PostMapping("/confirmorder")
	public String confirmOrder() {
		return restaurantUserService.confirmOrder();
	}

	@GetMapping("/vieworder")
	public List<RestaurantGeneratedBillsDTO> viewOrder() {
		return restaurantUserService.viewOrder();
	}

	@GetMapping("/viewgeneratedbills")
	public List<RestaurantGeneratedBillsDTO> viewGeneratedBills(
			@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
		return restaurantUserService.getGeneratedBills(fromDate, toDate);
	}

}
