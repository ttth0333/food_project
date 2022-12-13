package com.tringuyen.food_project.service;

import com.tringuyen.food_project.dto.RestaurantDTO;
import com.tringuyen.food_project.dto.RestaurantDetailDTO;
import com.tringuyen.food_project.entity.RestaurantEntity;
import com.tringuyen.food_project.entity.RestaurantReviewEntity;
import com.tringuyen.food_project.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService{
    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public List<RestaurantDTO> getRestaurant() {
        //Query database
        List<RestaurantDTO> dtos = new ArrayList<>();
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        // xử lí data [{title : "", image: "", avg = 3.7]}
        for (RestaurantEntity data: restaurantEntities) {
            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setTitle(data.getName());
//            "http://localhost:8080/api/" + data.getImage()
            restaurantDTO.setImage(data.getImage());

            float sumRate = 0;
            float avgRate = 0;
            for (RestaurantReviewEntity dataReview : data.getRestaurantReviews()) {
                sumRate += dataReview.getRate();
            } if (data.getRestaurantReviews().size() > 0) {
                avgRate = sumRate/ data.getRestaurantReviews().size();
            }

            restaurantDTO.setAvgRate(avgRate);
            dtos.add(restaurantDTO);
        }

        return dtos;
    }

    @Override
    public RestaurantDetailDTO getDetailRestaurant(int id) {
        //Optional : tức là có hoặc không có cũng được ( Dữ liệu có thể bị null )
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
        if(restaurantEntity.isPresent()){
            //Có giá trị thì xử lý
            restaurantDetailDTO.setTitle(restaurantEntity.get().getName());
            restaurantDetailDTO.setImage(restaurantEntity.get().getImage());
//            restaurantDetailDTO.setDesc();
            float avgRate = 0;
            float sumRate = 0;
            for (RestaurantReviewEntity dataReview: restaurantEntity.get().getRestaurantReviews()) {
                sumRate += dataReview.getRate();
            }
            if(restaurantEntity.get().getRestaurantReviews().size() > 0){
                avgRate = sumRate/restaurantEntity.get().getRestaurantReviews().size();
            }
            restaurantDetailDTO.setAvgRate(avgRate);
        }

        return restaurantDetailDTO;
    }
}
