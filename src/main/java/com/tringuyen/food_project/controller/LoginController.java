package com.tringuyen.food_project.controller;

import com.tringuyen.food_project.jwt.JwtTokenHelper;
import com.tringuyen.food_project.payload.request.SigninRequest;
import com.tringuyen.food_project.payload.response.DataResponse;
import com.tringuyen.food_project.payload.response.DataTokenResponse;
import com.tringuyen.food_project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin // Cho phép những domain khác với domain của api truy cập
@RequestMapping("/signin")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @GetMapping("/test")
    public String test() {
        return "heelo";
    }

    private long expiredDate = 8 * 60 * 60 * 1000;
    private long refreshExpiredDate = 80 * 60 * 60 * 1000;
    @PostMapping("")
    public ResponseEntity<?> signin(@RequestBody SigninRequest request){

//        boolean isSuccess = loginService.checkLogin(request.getEmail(), request.getPassword());
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication auth = authenticationManager.authenticate(authRequest);// dang nhap thanh cong chay 2 cai duoi. k thi xuat loi 403
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);

        String token = jwtTokenHelper.generateToken(request.getUsername(), "authen", expiredDate);
        String refreshToken = jwtTokenHelper.generateToken(request.getUsername(),"refresh", refreshExpiredDate);
//        String decodeToken = jwtTokenHelper.decodeToken(token);

        DataTokenResponse dataTokenResponse = new DataTokenResponse();
        dataTokenResponse.setToken(token);
        dataTokenResponse.setRefreshToken(refreshToken);

        DataResponse dataResponse = new DataResponse();
        dataResponse.setStatus(HttpStatus.OK.value());
        dataResponse.setSuccess(true);
        dataResponse.setDescription(""); /*decodeToken*/
        dataResponse.setData(dataTokenResponse);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }
}
