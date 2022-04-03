package com.greatlearning.surabi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author sriram
 *
 */
@Entity
@Immutable
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantUser {
	@Id
	private String username;
	private String password;
	private boolean enabled;

}
