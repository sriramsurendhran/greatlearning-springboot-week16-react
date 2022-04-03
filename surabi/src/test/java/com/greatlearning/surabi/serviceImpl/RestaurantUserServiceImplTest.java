package com.greatlearning.surabi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.greatlearning.surabi.entity.RestaurantItem;
import com.greatlearning.surabi.entity.RestaurantItemOrderDetails;
import com.greatlearning.surabi.entity.RestaurantOrderDetails;
import com.greatlearning.surabi.entity.RestaurantUser;
import com.greatlearning.surabi.model.RestaurantItemAddItemDTO;
import com.greatlearning.surabi.model.RestaurantOrderDetailsDTO;
import com.greatlearning.surabi.repository.RestaurantItemRepository;
import com.greatlearning.surabi.repository.RestaurantOrderDetailsRepository;
import com.greatlearning.surabi.repository.RestaurantUserRepository;
import com.greatlearning.surabi.utils.RestaurantUtils;

/**
 * @author sriram
 *
 */
@SpringBootTest
public class RestaurantUserServiceImplTest {

	@Mock
	RestaurantUserRepository restaurantUserRepository;

	@Mock
	JdbcUserDetailsManager jdbcUserDetailsManager;

	@Mock
	RestaurantItemRepository restaurantItemRepository;

	@InjectMocks
	RestaurantUserServiceImpl restaurantUserServiceImpl;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	RestaurantOrderDetails restaurantOrderDetails;

	@Mock
	RestaurantUtils<RestaurantOrderDetailsDTO, RestaurantOrderDetails> restaurantUtils;

	@Mock
	RestaurantOrderDetailsRepository restaurantOrderDetailsRepository;

	@Mock
	ModelMapper modelMapper;

	@Test
	public void shouldCreateUser() {
		String role = "ADMIN";
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername("TEST USER");

		Mockito.when(passwordEncoder.encode("TEST PASSWORD")).thenReturn("TEST PASSWORD");

		restaurantUser.setPassword(passwordEncoder.encode("TEST PASSWORD"));

		Mockito.doNothing().when(jdbcUserDetailsManager).createUser(User.withUsername(restaurantUser.getUsername())
				.password(restaurantUser.getPassword()).roles(role).build());

		String result = restaurantUserServiceImpl.createUser(role, restaurantUser);

		Mockito.verify(jdbcUserDetailsManager, Mockito.times(1)).createUser(User
				.withUsername(restaurantUser.getUsername()).password(restaurantUser.getPassword()).roles(role).build());

		Assertions.assertTrue(result.equals("User : " + restaurantUser.getUsername() + " successfully created !!!"));

	}

	@Test
	public void shouldThrowRuntimeExceptionWhenInvalidRoleWhileCreatingUser() {

		String role = "TEST";
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername("TEST USER");

		Mockito.when(passwordEncoder.encode("TEST PASSWORD")).thenReturn("TEST PASSWORD");

		restaurantUser.setPassword(passwordEncoder.encode("TEST PASSWORD"));

		Mockito.doNothing().when(jdbcUserDetailsManager).createUser(User.withUsername(restaurantUser.getUsername())
				.password(restaurantUser.getPassword()).roles(role).build());

		RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
				() -> restaurantUserServiceImpl.createUser(role, restaurantUser));

		Assertions.assertTrue(ex.getMessage()
				.contains("Role should be one of ADMIN,USER for user  :" + restaurantUser.getUsername()));

	}

	@Test
	public void shouldThrowRuntimeExceptionWhenCreatingAlreadyExistingUser() {
		String role = "ADMIN";
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername("TEST USER");

		Mockito.when(passwordEncoder.encode("TEST PASSWORD")).thenReturn("TEST PASSWORD");

		restaurantUser.setPassword(passwordEncoder.encode("TEST PASSWORD"));

		Mockito.when(restaurantUserRepository.existsById(restaurantUser.getUsername())).thenReturn(true);

		RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
				() -> restaurantUserServiceImpl.createUser(role, restaurantUser));

		Assertions.assertTrue(ex.getMessage().contains("User already exists !!! :" + restaurantUser.getUsername()));

	}

	@Test
	public void shouldRegisterUser() {
		String userName = "TEST USER";
		String password = "TEST PASSWORD";

		Mockito.when(passwordEncoder.encode(password)).thenReturn(password);

		Mockito.doNothing().when(jdbcUserDetailsManager)
				.createUser(User.withUsername(userName).password(password).roles("USER").build());

		String result = restaurantUserServiceImpl.registerUser(userName, password);

		Mockito.verify(jdbcUserDetailsManager, Mockito.times(1))
				.createUser(User.withUsername(userName).password(password).roles("USER").build());

		Assertions.assertTrue(result.equals("User : " + userName + " successfully created !!!"));

	}

	@Test
	public void shouldUpdateUser() {
		String role = "ADMIN";
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername("TEST USER");

		Mockito.when(passwordEncoder.encode("TEST PASSWORD")).thenReturn("TEST PASSWORD");

		restaurantUser.setPassword(passwordEncoder.encode("TEST PASSWORD"));

		Mockito.when(restaurantUserRepository.existsById(restaurantUser.getUsername())).thenReturn(true);

		Mockito.doNothing().when(jdbcUserDetailsManager).updateUser(User.withUsername(restaurantUser.getUsername())
				.password(restaurantUser.getPassword()).roles(role).build());

		String result = restaurantUserServiceImpl.updateUser(role, restaurantUser);

		Mockito.verify(jdbcUserDetailsManager, Mockito.times(1)).updateUser(User
				.withUsername(restaurantUser.getUsername()).password(restaurantUser.getPassword()).roles(role).build());

		Assertions.assertTrue(result.equals("User : " + restaurantUser.getUsername() + " successfully updated !!!"));

	}

	@Test
	public void shouldThrowRuntimeExceptionWhenUpdatingNonExistingUser() {
		String role = "ADMIN";
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername("TEST USER");

		Mockito.when(passwordEncoder.encode("TEST PASSWORD")).thenReturn("TEST PASSWORD");

		restaurantUser.setPassword(passwordEncoder.encode("TEST PASSWORD"));

		Mockito.when(restaurantUserRepository.existsById(restaurantUser.getUsername())).thenReturn(false);

		RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
				() -> restaurantUserServiceImpl.updateUser(role, restaurantUser));

		Assertions.assertTrue(ex.getMessage().contains("User does not exist :" + restaurantUser.getUsername()));

	}

	@Test
	public void shouldThrowRuntimeExceptionWhenInvalidRoleWhileUpdatingUser() {

		String role = "TEST";
		RestaurantUser restaurantUser = new RestaurantUser();
		restaurantUser.setUsername("TEST USER");

		Mockito.when(passwordEncoder.encode("TEST PASSWORD")).thenReturn("TEST PASSWORD");

		restaurantUser.setPassword(passwordEncoder.encode("TEST PASSWORD"));

		RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
				() -> restaurantUserServiceImpl.updateUser(role, restaurantUser));

		Assertions.assertTrue(ex.getMessage()
				.contains("Role should be one of ADMIN,USER for user  :" + restaurantUser.getUsername()));

	}

	@Test
	public void shouldDeleteUser() {
		String userName = "TEST USER";
		Mockito.when(restaurantUserRepository.existsById(userName)).thenReturn(true);

		Mockito.doNothing().when(jdbcUserDetailsManager).deleteUser(userName);

		String result = restaurantUserServiceImpl.deleteUser(userName);

		Mockito.verify(jdbcUserDetailsManager, Mockito.times(1)).deleteUser(userName);

		Assertions.assertTrue(result.equals("User : " + userName + " successfully Deleted !!!"));

	}

	@Test
	public void shouldThrowRuntimeExceptionWhenDeletingNonExistingUser() {
		String userName = "TEST USER";
		Mockito.when(restaurantUserRepository.existsById(userName)).thenReturn(false);

		RuntimeException ex = Assertions.assertThrows(RuntimeException.class,
				() -> restaurantUserServiceImpl.deleteUser(userName));

		Assertions.assertTrue(ex.getMessage().contains("User does not exist :" + userName));

	}

	@Test
	public void shouldAddItemstoOrder() {
		List<RestaurantItemAddItemDTO> restaurantItemAddItemDTOList = new ArrayList<>();
		restaurantItemAddItemDTOList.add(new RestaurantItemAddItemDTO(1));
		restaurantItemAddItemDTOList.add(new RestaurantItemAddItemDTO(2));

		RestaurantOrderDetailsDTO restaurantOrderDetailsDTO = new RestaurantOrderDetailsDTO(
				restaurantItemAddItemDTOList);

		Mockito.when(restaurantUtils.getLoggedInUser()).thenReturn("TEST USER");

		/*
		 * Mockito.when(restaurantOrderDetailsDTO.getRestaurantItemAddItemDTO().stream()
		 * .filter(item ->
		 * !restaurantItemRepository.existsById(item.getItemId())).count()).thenReturn(0
		 * );
		 */

		Mockito.when(restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(false);

		Mockito.when(restaurantOrderDetailsRepository.save(restaurantOrderDetails))
				.thenReturn(new RestaurantOrderDetails());

		Mockito.when(restaurantItemRepository.existsById(1L)).thenReturn(true);
		Mockito.when(restaurantItemRepository.existsById(2L)).thenReturn(true);

		Mockito.when(restaurantItemRepository.findById(1L))
				.thenReturn(Optional.of(new RestaurantItem(1, "Burger", 100)));
		Mockito.when(restaurantItemRepository.findById(2L)).thenReturn(Optional.of(new RestaurantItem(2, "Pizza", 50)));

		Mockito.when(modelMapper.map(restaurantOrderDetailsDTO, RestaurantOrderDetails.class))
				.thenReturn(new RestaurantOrderDetails());

		restaurantOrderDetailsDTO.getRestaurantItemAddItemDTO().forEach((r) -> {
			Mockito.when(modelMapper.map(r, RestaurantItemOrderDetails.class))
					.thenReturn(new RestaurantItemOrderDetails());
		});

		restaurantUserServiceImpl.addItemstoOrder(restaurantOrderDetailsDTO);

		// Mockito.verify(restaurantOrderDetailsRepository,
		// Mockito.times(1)).save(restaurantOrderDetails);
	}

	@Test
	public void shouldConfirmOrder() {
		Mockito.when(restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(true);
		Mockito.when(restaurantOrderDetailsRepository.findByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(new RestaurantOrderDetails());

		Mockito.when(restaurantOrderDetailsRepository.save(restaurantOrderDetails))
				.thenReturn(new RestaurantOrderDetails());
	}

	@Test
	public void shouldViewOrder() {

		List<RestaurantOrderDetails> resultList = new ArrayList<RestaurantOrderDetails>();
		resultList.add(new RestaurantOrderDetails());

		Mockito.when(restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(true);
		Mockito.when(restaurantOrderDetailsRepository.findAllByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(resultList);
	}

	@Test
	public void shouldGetGeneratedBills() {
		List<RestaurantOrderDetails> resultList = new ArrayList<RestaurantOrderDetails>();
		Mockito.when(restaurantOrderDetailsRepository.existsByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(true);
		Mockito.when(restaurantOrderDetailsRepository.findAllByOrderStatusAndOrderUserName("CREATED",
				restaurantUtils.getLoggedInUser())).thenReturn(resultList);
	}

}
