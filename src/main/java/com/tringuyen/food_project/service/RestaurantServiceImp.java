package com.tringuyen.food_project.service;

import com.google.gson.Gson;
import com.tringuyen.food_project.dto.RestaurantDTO;
import com.tringuyen.food_project.dto.RestaurantDetailDTO;
import com.tringuyen.food_project.entity.FoodEntity;
import com.tringuyen.food_project.entity.RestaurantEntity;
import com.tringuyen.food_project.entity.RestaurantReviewEntity;
import com.tringuyen.food_project.model.FoodModel;
import com.tringuyen.food_project.repository.RestaurantRepository;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImp implements RestaurantService{

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RedisTemplate redisTemplate;

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
    // memcache
//    @Cacheable("detail_restaurant")
    public RestaurantDetailDTO getDetailRestaurant(int id) {
        String key = "red" + id;
        Gson gson = new Gson();
        RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();

        //check key có tồn tại hay không
        if (redisTemplate.hasKey(key)) {
            //có tồn tại
            String data = (String) redisTemplate.opsForValue().get(key);
            //Biến data thành đối tượng RestaurantDetailDTO
            restaurantDetailDTO = gson.fromJson(data, RestaurantDetailDTO.class);
        } else {
            //không tồn tại
            //Optional : tức là có hoặc không có cũng được ( Dữ liệu có thể bị null )
            Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
//            RestaurantDetailDTO restaurantDetailDTO = new RestaurantDetailDTO();
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

                List<FoodModel> foodModels = new ArrayList<>();
                for (FoodEntity foodEntity: restaurantEntity.get().getFoods()) {
                    FoodModel foodModel = new FoodModel();
                    foodModel.setId(foodEntity.getId());
                    foodModel.setName(foodEntity.getName());
                    foodModel.setImage(foodEntity.getImage());
                    foodModel.setPrice(foodEntity.getPrice());

                    // Add foodModel trong foreach vao list
                    foodModels.add(foodModel);
                }
                restaurantDetailDTO.setFood(foodModels);
            }

            //cache redis

            String json = gson.toJson(restaurantDetailDTO);
            redisTemplate.opsForValue().set(id, json);
        }




        return restaurantDetailDTO;
    }


    @CacheEvict( value = "detail_restaurent", allEntries = true)
    @Override
    public void clearAllCache() {

    }
}
