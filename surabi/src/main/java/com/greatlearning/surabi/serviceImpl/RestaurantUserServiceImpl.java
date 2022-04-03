package com.greatlearning.surabi.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.greatlearning.surabi.entity.RestaurantItemOrderDetails;
import com.greatlearning.surabi.entity.RestaurantOrderDetails;
import com.greatlearning.surabi.entity.RestaurantUser;
import com.greatlearning.surabi.model.RestaurantGeneratedBillsDTO;
import com.greatlearning.surabi.model.RestaurantOrderDetailsDTO;
import com.greatlearning.surabi.model.RestaurantOrderedItemDTO;
import com.greatlearning.surabi.repository.RestaurantUserRepository;
import com.greatlearning.surabi.repository.RestaurantItemOrderDetailsRepository;
import com.greatlearning.surabi.repository.RestaurantItemRepository;
import com.greatlearning.surabi.repository.RestaurantOrderDetailsRepository;
import com.greatlearning.surabi.service.RestaurantUserService;
import com.greatlearning.surabi.utils.RestaurantUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * @author sriram
 *
 */
@Service
@Slf4j
public class RestaurantUserServiceImpl implements RestaurantUserService {

	@Autowired
	RestaurantUserRepository restaurantUserRepository;

	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	RestaurantItemRepository restaurantItemRepository;

	@Autowired
	RestaurantOrderDetailsRepository restaurantOrderDetailsRepository;

	@Autowired
	RestaurantItemOrderDetailsRepository restaurantItemOrderDetailsRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RestaurantUtils<RestaurantOrderDetailsDTO, RestaurantOrderDetails> restaurantUtils;

	@Override
	public List<RestaurantUser> getAllUsers() {
		return restaurantUserRepository.findAll();
	}

	@Override
	public String registerUser(String userName, String password) {
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername(userName);
		restaurantUser.setEnabled(true);
		restaurantUser.setPassword(passwordEncoder.encode(password));

		jdbcUserDetailsManager.createUser(User.withUsername(restaurantUser.getUsername())
				.password(restaurantUser.getPassword()).roles("USER").build());

		return "User : " + restaurantUser.getUsername() + " successfully created !!!";
	}

	@Override
	public String createUser(String role, RestaurantUser restaurantUser) {

		if (!role.equals("ADMIN") && !role.equals("USER")) {
			throw new RuntimeException("Role should be one of ADMIN,USER for user  :" + restaurantUser.getUsername());
		}

		if (restaurantUserRepository.existsById(restaurantUser.getUsername())) {
			throw new RuntimeException("User already exists !!! :" + restaurantUser.getUsername());
		} else {
			restaurantUser.setPassword(passwordEncoder.encode(restaurantUser.getPassword()));

			jdbcUserDetailsManager.createUser(User.withUsername(restaurantUser.getUsername())
					.password(restaurantUser.getPassword()).roles(role).build());

			return "User : " + restaurantUser.getUsername() + " successfully created !!!";
		}
	}

	@Override
	public String updateUser(String role, RestaurantUser restaurantUser) {

		if (!role.equals("ADMIN") && !role.equals("USER")) {
			throw new RuntimeException("Role should be one of ADMIN,USER for user  :" + restaurantUser.getUsername());
		}

		if (restaurantUserRepository.existsById(restaurantUser.getUsername())) {
			restaurantUser.setPassword(passwordEncoder.encode(restaurantUser.getPassword()));

			jdbcUserDetailsManager.updateUser(User.withUsername(restaurantUser.getUsername())
					.password(restaurantUser.getPassword()).roles(role).build());

			return "User : " + restaurantUser.getUsername() + " successfully updated !!!";
		} else {
			throw new RuntimeException("User does not exist :" + restaurantUser.getUsername());
		}
	}

	@Override
	public String deleteUser(String userName) {
		if (restaurantUserRepository.existsById(userName)) {
			jdbcUserDetailsManager.deleteUser(userName);
			return "User : " + userName + " successfully Deleted !!!";
		} else {
			throw new RuntimeException("User does not exist :" + userName);
		}
	}

	@Override
	public String addItemstoOrder(RestaurantOrderDetailsDTO restaurantOrderDetailsDTO) {

		if (restaurantOrderDetailsDTO.getRestaurantItemAddItemDTO().stream()
				.filter(item -> !restaurantItemRepository.existsById(item.getItemId())).count() > 0) {
			throw new RuntimeException("Error adding items, item not found in menu");
		}

		RestaurantOrderDetails restaurantOrderDetails;
		if (restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())) {
			restaurantOrderDetails = restaurantOrderDetailsRepository.findByOrderStatusAndOrderUserName("CREATED",
					restaurantUtils.getLoggedInUser());
		} else {
			restaurantOrderDetails = modelMapper.map(restaurantOrderDetailsDTO, RestaurantOrderDetails.class);
			restaurantOrderDetails.setOrderStatus("CREATED");
			restaurantOrderDetails.setOrderUserName(restaurantUtils.getLoggedInUser());
		}
		List<RestaurantItemOrderDetails> rs = new ArrayList<>();

		restaurantOrderDetailsDTO.getRestaurantItemAddItemDTO().forEach((r) -> {
			RestaurantItemOrderDetails restaurantItemOrderDetails = modelMapper.map(r,
					RestaurantItemOrderDetails.class);

			restaurantItemOrderDetails.setRestaurantItem(restaurantItemRepository.findById(r.getItemId()).get());
			restaurantItemOrderDetails.setPrice(restaurantItemOrderDetails.getRestaurantItem().getPrice());
			rs.add(restaurantItemOrderDetails);
			restaurantOrderDetails.setRestaurantItemOrderDetails(rs);
			restaurantItemOrderDetails.setRestaurantOrderDetails(restaurantOrderDetails);
		});

		restaurantOrderDetails.setOrderBillAmount(restaurantOrderDetails.getOrderBillAmount() + restaurantOrderDetails
				.getRestaurantItemOrderDetails().stream().mapToDouble(RestaurantItemOrderDetails::getPrice).sum());
		restaurantOrderDetailsRepository.save(restaurantOrderDetails);
		return "Items successfully added to Order No :" + restaurantOrderDetails.getOrderId();
	}

	@Override
	public String confirmOrder() {
		RestaurantOrderDetails restaurantOrderDetails;
		if (restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())) {
			restaurantOrderDetails = restaurantOrderDetailsRepository.findByOrderStatusAndOrderUserName("CREATED",
					restaurantUtils.getLoggedInUser());
			restaurantOrderDetails.setOrderStatus("PAID");
			restaurantOrderDetailsRepository.save(restaurantOrderDetails);
			return "Order No : " + restaurantOrderDetails.getOrderId() + " for user "
					+ restaurantUtils.getLoggedInUser() + " Confirmed and Bill Paid";
		} else {
			throw new RuntimeException("No order created for the user : " + restaurantUtils.getLoggedInUser()
					+ " to confirm and generate bill");
		}
	}

	@Override
	public List<RestaurantGeneratedBillsDTO> viewOrder() {
		List<RestaurantOrderDetails> restaurantOrderDetails;
		if (restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())) {
			restaurantOrderDetails = restaurantOrderDetailsRepository.findAllByOrderStatusAndOrderUserName("CREATED",
					restaurantUtils.getLoggedInUser());
		} else {
			throw new RuntimeException("No orders created for the user : " + restaurantUtils.getLoggedInUser()
					+ " ,click addItemsToOrder to create Order");
		}

		List<RestaurantGeneratedBillsDTO> restaurantGeneratedBillsDTOList = new ArrayList<>();

		restaurantOrderDetails.forEach(r -> {
			RestaurantGeneratedBillsDTO restaurantGeneratedBillsDTO = RestaurantUtils.convertMapper(r,
					RestaurantGeneratedBillsDTO.class);

			r.getRestaurantItemOrderDetails().forEach(res -> {
				RestaurantOrderedItemDTO restaurantOrderedItemDTO = new RestaurantOrderedItemDTO(
						res.getRestaurantItem().getItemId(), res.getRestaurantItem().getItemName(),
						res.getRestaurantItem().getPrice());
				restaurantGeneratedBillsDTO.getRestaurantOrderedItemDTO().add(restaurantOrderedItemDTO);

			});
			restaurantGeneratedBillsDTOList.add(restaurantGeneratedBillsDTO);
		});

		return restaurantGeneratedBillsDTOList;

	}

	@Override
	public List<RestaurantGeneratedBillsDTO> getGeneratedBills(LocalDate fromDate, LocalDate toDate) {
		List<RestaurantOrderDetails> restaurantOrderDetails = restaurantOrderDetailsRepository
				.findAllByOrderDateBetweenAndOrderStatus(fromDate.atStartOfDay(), toDate.atTime(LocalTime.MAX), "PAID");

		List<RestaurantGeneratedBillsDTO> restaurantGeneratedBillsDTOList = new ArrayList<>();
		restaurantOrderDetails.forEach(r -> {
			RestaurantGeneratedBillsDTO restaurantGeneratedBillsDTO = RestaurantUtils.convertMapper(r,
					RestaurantGeneratedBillsDTO.class);

			r.getRestaurantItemOrderDetails().forEach(res -> {
				RestaurantOrderedItemDTO restaurantOrderedItemDTO = new RestaurantOrderedItemDTO(
						res.getRestaurantItem().getItemId(), res.getRestaurantItem().getItemName(),
						res.getRestaurantItem().getPrice());
				restaurantGeneratedBillsDTO.getRestaurantOrderedItemDTO().add(restaurantOrderedItemDTO);
			});
			restaurantGeneratedBillsDTOList.add(restaurantGeneratedBillsDTO);
		});
		return restaurantGeneratedBillsDTOList;
	}
}
