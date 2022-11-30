package com.tringuyen.food_project.service;

import com.tringuyen.food_project.entity.UserEntity;

import java.util.List;

public interface LoginService {
    boolean checkLogin(String email, String password);
    UserEntity checkLogin(String email);
}
