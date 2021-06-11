package uz.pdp.appnewssite.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appnewssite.entity.Role;

import java.util.Date;

@Component
public class JwtProvider {

    static long expireTime = 1000 * 3600;// 1soat
    static String secretKey = "BuniSenBilolmaysan";


    //generatsiya
    public String generateToken(String username, Role role) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles",role.getName())
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    //token aynan yaroqli va bzniki ekanligini tekshirish
    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //tokendan uni usernameni ajratib olish un metod
    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();//username
    }
}