package com.tringuyen.food_project.repository;

import com.tringuyen.food_project.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Integer> {
}
