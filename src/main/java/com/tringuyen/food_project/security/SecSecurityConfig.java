package com.tringuyen.food_project.security;

import com.tringuyen.food_project.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    // Dùng để khởi tạo danh sách user cứng và danh sách user này sẽ đc lưu ở RAM
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user1 = User.withUsername("user1")
//                .password(passwordEncoder().encode("user123"))
//                .roles("USERS").build();
//
//        UserDetails user2 = User.withUsername("user2")
//                .password(passwordEncoder().encode("admin123"))
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }

    //Kiểu mã hoá dữ liệu
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //Nơi quy đinh các rule liên quan tới bảo mật và quyền truy cập
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // khong su dung session
                .and()
                .authorizeRequests()
                .antMatchers("/signin") //định nghĩa link cần xác thực
                .permitAll()
                .antMatchers("/refresh-token").permitAll()
                .antMatchers("/signin/test").authenticated()// Cho phép truy cập full quyền vào link chỉ định
                .anyRequest()// toàn bộ request gọi vào api
                .authenticated(); //bắt buộc phải chứng thực(đăng nhập) mới được gọi

        //Add filter trước fiter nào đó
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
