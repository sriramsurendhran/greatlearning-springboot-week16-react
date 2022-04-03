package com.greatlearning.surabi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.surabi.entity.RestaurantItemOrderDetails;


/**
 * @author sriram
 *
 */
@Repository
public interface RestaurantItemOrderDetailsRepository extends CrudRepository<RestaurantItemOrderDetails, Long> {

}
