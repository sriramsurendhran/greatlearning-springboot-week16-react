package com.greatlearning.surabi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.surabi.entity.RestaurantItem;


/**
 * @author sriram
 *
 */
@Repository
public interface RestaurantItemRepository extends JpaRepository<RestaurantItem, Long> {

}
