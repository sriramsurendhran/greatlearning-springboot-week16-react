package com.greatlearning.surabi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.surabi.entity.RestaurantUser;


/**
 * @author sriram
 *
 */
@Repository
public interface RestaurantUserRepository extends JpaRepository<RestaurantUser, String> {

}
