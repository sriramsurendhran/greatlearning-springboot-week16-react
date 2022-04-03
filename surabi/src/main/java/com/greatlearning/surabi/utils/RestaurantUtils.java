package com.greatlearning.surabi.utils;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.greatlearning.surabi.model.RestaurantGeneratedBillsDTO;


/**
 * @author sriram
 *
 * @param <S>
 * @param <T>
 */
@Component
public class RestaurantUtils<S, T> {

	public String getLoggedInUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		return username;
	}

	public static <S, T> T convertMapper(S source, Class<T> target) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(source, target);
	}
}
