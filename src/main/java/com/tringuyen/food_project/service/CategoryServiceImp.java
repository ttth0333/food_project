package com.tringuyen.food_project.service;

import com.tringuyen.food_project.entity.CategoryEntity;
import com.tringuyen.food_project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getExplorerCategory() {
        return categoryRepository.getExplorer();


    }
}
