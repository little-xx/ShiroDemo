package com.littlexx.shiro.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {


    private static final long serialVersionUID = -9096911230149041482L;

    // 设置过期时间为30分钟
    private static final long EXPIRE_TIME  = 30*60*1000;

    public long getExpireTime() {
        return EXPIRE_TIME;
    }

    private DecodedJWT decodedJWT(String token) {
        DecodedJWT jwt;
        try {
            jwt = JWT.decode(token);
        } catch (Exception e) {
            jwt = null;
        }
        return jwt;
    }


    public String getUsernameFromToken(String token) {
        String username;
        try {
            final DecodedJWT jwt = decodedJWT(token);
            username = jwt.getClaim("username").asString();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final DecodedJWT jwt = decodedJWT(token);
            expiration = jwt.getExpiresAt();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }

    private Map<String, Object> generateHeader() {
        Map<String, Object> header = new HashedMap();
        header.put("alg", "HS512");
        header.put("typ", "JWT");
        return header;
    }

    public String generateToken(String username, String secret, String userId) {
        return JWT.create()
                .withHeader(generateHeader())
                .withClaim("username", username)
                .withExpiresAt(generateExpirationDate())
                .withIssuedAt(new Date())
                .withSubject(userId)
                .sign(Algorithm.HMAC256(secret));
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public Boolean validateToken(String token, String username, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
