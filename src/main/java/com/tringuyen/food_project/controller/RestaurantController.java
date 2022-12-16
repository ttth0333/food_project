package com.tringuyen.food_project.controller;

import com.tringuyen.food_project.dto.RestaurantDTO;
import com.tringuyen.food_project.dto.RestaurantDetailDTO;
import com.tringuyen.food_project.entity.RestaurantEntity;
import com.tringuyen.food_project.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    @Autowired
    RestaurantService restaurantService;

    @GetMapping("")
    public ResponseEntity<?> getRestaurant() {
        List<RestaurantDTO> responseEntities = restaurantService.getRestaurant();
        System.out.println("kiemtra " + responseEntities.size());
        return new ResponseEntity<>(responseEntities, HttpStatus.OK);
    }

    @GetMapping("/clear-cache")
    public ResponseEntity<?> clearCacheRestaurant() {
        restaurantService.clearAllCache();
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailRestaurant(@PathVariable("id") int id) {
        RestaurantDetailDTO detailDTO = restaurantService.getDetailRestaurant(id);

        return new ResponseEntity<>(detailDTO, HttpStatus.OK);
    }
}