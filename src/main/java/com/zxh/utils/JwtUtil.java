package com.zxh.utils;

import com.zxh.entity.Users;
import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {

    private static long time = 1000*60*600;//
    private static String signatrue = "zxh";

    public static String createToken(Users user) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("username", user.getUsername())
                .claim("id", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                //signatrue
                .signWith(SignatureAlgorithm.HS256, signatrue)
                .compact();
        return jwtToken;
    }

    public static boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signatrue).parseClaimsJws(token);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public static Claims parseJwt(String token){
        try {
            return Jwts.parser().setSigningKey(signatrue).parseClaimsJws(token).getBody();
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
