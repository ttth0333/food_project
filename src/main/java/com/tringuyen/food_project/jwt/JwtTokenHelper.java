package com.tringuyen.food_project.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper {

//    private long expiredDate = 8 * 60 * 60 * 1000;
    private final String strKey = "xJHDonkgbMOgIGNodeG7l2kgYuG6o28gbeG6rXQgxJHhuqd5IMSR4bunIDI1NiBiaXQ="; // chuỗi base 64
    private Gson gson = new Gson();

    public String generateToken(String data, String type ,long expiredDate) {
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + expiredDate);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        Map<String, Object> subjectData = new HashMap<>();
        // nên quy về đối tượng
        subjectData.put("username", data);
        subjectData.put("type", type);


        String json = gson.toJson(subjectData); //{"type":"refresh","username":"nguyenvana@gmail.com"}

        return Jwts.builder()
                .setSubject(json) // lưu trữ dữ liệu vào token = kiểu String
                .setIssuedAt(now) // thời gian tạo ra token
                .setExpiration(dateExpired) // thời gian hết hạn token
                .signWith(secretKey, SignatureAlgorithm.HS256) // thuật toán mã hoá và secret key
                .compact(); // trả token đã được mã hoá
    }

    public String decodeToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));

        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(strKey));
        boolean isSuccess= false;
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            isSuccess = true;
        } catch (MalformedJwtException ex) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("JWT claims string is empty.");
        }
    return isSuccess;
    }

}
