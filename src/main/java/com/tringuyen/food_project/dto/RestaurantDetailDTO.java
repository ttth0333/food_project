package com.tringuyen.food_project.dto;

import com.tringuyen.food_project.entity.FoodEntity;
import com.tringuyen.food_project.model.FoodModel;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RestaurantDetailDTO {
    private String title = "";
    private String image = "";
    private float avgRate = 0;
    private String desc = "";

    private List<FoodModel> food;

    public List<FoodModel> getFood() {
        return food;
    }

    public void setFood(List<FoodModel> food) {
        this.food = food;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(float avgRate) {
        this.avgRate = avgRate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
