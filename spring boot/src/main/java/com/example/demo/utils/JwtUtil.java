package com.example.demo.utils;

import java.util.Date;
import java.util.UUID;

import com.example.demo.entity.User;

import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    //token有效期，默认为 1 天
    private static long expiration = 1000 * 24 * 60 * 60;
    private static String signature = "token-signature";

    /**
     * 使用 userId 和 userName 生成 token
     * @param user
     * @return
     */
    public static String createToken(User user) {
        JwtBuilder jwtbuilder = Jwts.builder();
        String token = jwtbuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("userId", user.getId())
                .claim("userName", user.getName())
                //主题
                .setSubject("user-token")
                //有效期
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setId(UUID.randomUUID().toString())
                //签名
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();

        return token;                                
    }

    public static User parseToken(String token) {
        JwtParser parser = Jwts.parser();
        Jws<Claims> claims = parser.setSigningKey(signature).parseClaimsJws(token);
        Claims claimsBody = claims.getBody();

        User user = new User();
        user.setId((Integer)claimsBody.get("userId"));
        user.setName(claimsBody.get("userName").toString());

        return user;
    }
    
}
