package com.tringuyen.food_project.repository;

import com.tringuyen.food_project.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    //tra dnah sach user check coi co ton tai hay k
    List<UserEntity> findByEmailAndPassword(String email, String password);
    List<UserEntity> findByEmail(String email);
}
