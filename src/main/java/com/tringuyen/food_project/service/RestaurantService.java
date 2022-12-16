package com.tringuyen.food_project.service;

import com.tringuyen.food_project.dto.RestaurantDTO;
import com.tringuyen.food_project.dto.RestaurantDetailDTO;
import com.tringuyen.food_project.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantService {
    // trả danh sách restaurant entity
    List<RestaurantDTO> getRestaurant();
    RestaurantDetailDTO getDetailRestaurant(int id);
    void clearAllCache();
}
