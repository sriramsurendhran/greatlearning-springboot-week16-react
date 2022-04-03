package com.greatlearning.surabi.controller;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greatlearning.surabi.entity.RestaurantItem;
import com.greatlearning.surabi.entity.RestaurantUser;
import com.greatlearning.surabi.model.RestaurantGeneratedBillsDTO;
import com.greatlearning.surabi.model.RestaurantItemAddItemDTO;
import com.greatlearning.surabi.model.RestaurantOrderDetailsDTO;
import com.greatlearning.surabi.model.RestaurantOrderedItemDTO;
import com.greatlearning.surabi.service.RestaurantItemService;
import com.greatlearning.surabi.service.RestaurantUserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@AutoConfigureTestDatabase
public class RestaurantControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	RestaurantItemService restaurantItemService;

	@MockBean
	RestaurantUserService restaurantUserService;

	@Autowired
	Environment environment;

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldGetAllItems() throws Exception {

		List<RestaurantItem> restaurantItemList = new ArrayList<>();
		restaurantItemList.add(new RestaurantItem(1, "pizza", 100));
		restaurantItemList.add(new RestaurantItem(1, "burger", 50));
		Mockito.when(restaurantItemService.getAllItems()).thenReturn(restaurantItemList);

		MvcResult responseObject = mockMvc.perform(get("/surabi/menu")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].itemName", Matchers.equalTo("pizza")))
				.andExpect(jsonPath("$[1].itemName", Matchers.equalTo("burger"))).andReturn();
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldGetAllUsers() throws Exception {
		List<RestaurantUser> restaurantUserList = new ArrayList<>();
		restaurantUserList.add(new RestaurantUser("sriram", "password", true));
		restaurantUserList.add(new RestaurantUser("test", "welcome", true));
		Mockito.when(restaurantUserService.getAllUsers()).thenReturn(restaurantUserList);

		MvcResult responseObject = mockMvc.perform(get("/surabi/listusers")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(2)))
				.andExpect(jsonPath("$[0].username", Matchers.equalTo("sriram")))
				.andExpect(jsonPath("$[1].username", Matchers.equalTo("test"))).andReturn();
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldRegisterUser() throws Exception {
		String userName = "sriram";
		String password = "password";
		Mockito.when(restaurantUserService.registerUser(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
				.thenReturn("User : " + userName + " successfully created !!!");
		MvcResult responseObject = mockMvc
				.perform(post("/surabi/registeruser").param("userName", userName).param("password", password))
				.andReturn();

		String result = responseObject.getResponse().getContentAsString();
		Assertions.assertTrue(result.equals("User : " + userName + " successfully created !!!"));
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldCreateUser() throws Exception {
		String userName = "tt2";
		String password = "pass";
		String role = "USER";
		RestaurantUser restaurantUser = new RestaurantUser(userName, password, true);
		Mockito.when(restaurantUserService.createUser(role, restaurantUser))
				.thenReturn("User : " + userName + " successfully created !!!");
		MvcResult responseObject = mockMvc.perform(post("/surabi/createuser").param("enabled", "true")
				.param("password", password).param("role", role).param("username", userName)).andReturn();
		String result = responseObject.getResponse().getContentAsString();
		Assertions.assertTrue(result.equals("User : " + userName + " successfully created !!!"));
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldUpdateUser() throws Exception {
		String userName = "tt2";
		String password = "pass";
		String role = "USER";
		RestaurantUser restaurantUser = new RestaurantUser(userName, password, true);
		Mockito.when(restaurantUserService.updateUser(role, restaurantUser))
				.thenReturn("User : " + userName + " successfully updated !!!");
		MvcResult responseObject = mockMvc.perform(put("/surabi/updateuser").param("enabled", "true")
				.param("password", password).param("role", role).param("username", userName)).andReturn();
		String result = responseObject.getResponse().getContentAsString();
		Assertions.assertTrue(result.equals("User : " + userName + " successfully updated !!!"));
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldDeleteUser() throws Exception {
		String userName = "tt2";
		Mockito.when(restaurantUserService.deleteUser(ArgumentMatchers.anyString()))
				.thenReturn("User : " + userName + " successfully Deleted !!!");
		MvcResult responseObject = mockMvc.perform(post("/surabi/deleteuser").param("userName", userName)).andReturn();
		String result = responseObject.getResponse().getContentAsString();
		Assertions.assertTrue(result.equals("User : " + userName + " successfully Deleted !!!"));
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldAddItemsToOrder() throws Exception {
		List<RestaurantItemAddItemDTO> restaurantItemAddItemDTOList = new ArrayList<>();
		restaurantItemAddItemDTOList.add(new RestaurantItemAddItemDTO(1L));
		restaurantItemAddItemDTOList.add(new RestaurantItemAddItemDTO(2L));
		RestaurantOrderDetailsDTO restaurantOrderDetailsDTO = new RestaurantOrderDetailsDTO(
				restaurantItemAddItemDTOList);

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(restaurantOrderDetailsDTO);
		Mockito.when(restaurantUserService.addItemstoOrder(restaurantOrderDetailsDTO))
				.thenReturn("Items successfully added to Order No :" + "1");
		MvcResult responseObject = mockMvc.perform(post("/surabi/addItemstoOrder").accept(MediaType.ALL)
				.contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("UTF-8")).andReturn();
		String result = responseObject.getResponse().getContentAsString();
		Assertions.assertTrue(result.equals("Items successfully added to Order No :" + "1"));
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldConfirmOrder() throws Exception {
		String userName = "tt2";
		Mockito.when(restaurantUserService.confirmOrder())
				.thenReturn("Order No : " + "1" + " for user " + userName + " Confirmed and Bill Paid");
		MvcResult responseObject = mockMvc.perform(post("/surabi/confirmorder").param("userName", userName))
				.andReturn();
		String result = responseObject.getResponse().getContentAsString();
		Assertions
				.assertTrue(result.equals("Order No : " + "1" + " for user " + userName + " Confirmed and Bill Paid"));
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldViewOrder() throws Exception {
		List<RestaurantOrderedItemDTO> restaurantOrderedItemDTOList = new ArrayList<>();
		restaurantOrderedItemDTOList.add(new RestaurantOrderedItemDTO(1, "pizza", 50));
		restaurantOrderedItemDTOList.add(new RestaurantOrderedItemDTO(1, "burger", 100));

		RestaurantGeneratedBillsDTO restaurantGeneratedBillsDTO = new RestaurantGeneratedBillsDTO();
		restaurantGeneratedBillsDTO.setOrderId(1);
		restaurantGeneratedBillsDTO.setOrderBillAmount(250);
		restaurantGeneratedBillsDTO.setOrderStatus("CREATED");
		restaurantGeneratedBillsDTO.setOrderUserName("test1");
		restaurantGeneratedBillsDTO.setRestaurantOrderedItemDTO(restaurantOrderedItemDTOList);

		List<RestaurantGeneratedBillsDTO> restaurantGeneratedBillsDTOList = new ArrayList<>();
		restaurantGeneratedBillsDTOList.add(restaurantGeneratedBillsDTO);

		Mockito.when(restaurantUserService.viewOrder()).thenReturn(restaurantGeneratedBillsDTOList);

		MvcResult responseObject = mockMvc.perform(get("/surabi/vieworder")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].orderUserName", Matchers.equalTo("test1"))).andReturn();
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldGetGeneratedBills() throws Exception {
		List<RestaurantOrderedItemDTO> restaurantOrderedItemDTOList = new ArrayList<>();
		restaurantOrderedItemDTOList.add(new RestaurantOrderedItemDTO(1, "pizza", 50));
		restaurantOrderedItemDTOList.add(new RestaurantOrderedItemDTO(1, "burger", 100));

		RestaurantGeneratedBillsDTO restaurantGeneratedBillsDTO = new RestaurantGeneratedBillsDTO();
		restaurantGeneratedBillsDTO.setOrderId(1);
		restaurantGeneratedBillsDTO.setOrderBillAmount(250);
		restaurantGeneratedBillsDTO.setOrderStatus("PAID");
		restaurantGeneratedBillsDTO.setOrderUserName("test1");
		restaurantGeneratedBillsDTO.setRestaurantOrderedItemDTO(restaurantOrderedItemDTOList);

		List<RestaurantGeneratedBillsDTO> restaurantGeneratedBillsDTOList = new ArrayList<>();
		restaurantGeneratedBillsDTOList.add(restaurantGeneratedBillsDTO);

		Mockito.when(restaurantUserService.getGeneratedBills(ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(restaurantGeneratedBillsDTOList);

		MvcResult responseObject = mockMvc
				.perform(
						get("/surabi/viewgeneratedbills").param("fromDate", "2021-09-01").param("toDate", "2021-11-30"))
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].orderUserName", Matchers.equalTo("test1"))).andReturn();
	}

	@Test
	public void shouldLoginUser() throws Exception {
		RequestBuilder requestBuilder = get("/login").param("username", "user").param("password", "password");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "sriram", roles = "ADMIN")
	public void shouldLogoutUser() throws Exception {
		RequestBuilder requestBuilder = get("/login?logout");
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
}
