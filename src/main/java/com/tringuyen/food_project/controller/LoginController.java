package com.tringuyen.food_project.controller;

import com.tringuyen.food_project.payload.request.SigninRequest;
import com.tringuyen.food_project.payload.response.DataResponse;
import com.tringuyen.food_project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin // Cho phép những domain khác với domain của api truy cập
@RequestMapping("/signin")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request){

        boolean isSuccess = loginService.checkLogin(request.getEmail(), request.getPassword());

        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(isSuccess);
        dataResponse.setDescription("");
        dataResponse.setData("");

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
