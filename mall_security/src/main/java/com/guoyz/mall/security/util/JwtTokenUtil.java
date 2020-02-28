package com.guoyz.mall.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author:guoyz
 * @Date 2020/2/26 21:07
 */
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    /**
     * 负责生成JWT的token
     * */
    
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }
    
    /**
     * 生成过期时间
     * */
    
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
    
    /**
     * 从token 中获取JWT的负载
     * */
    private Claims getClaimsFromToken(String token){
        Claims  claims = null;
        claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }
    
    /**
     * 从token获取登录名
     * */
    
    public String getUsernameFromToken(String token){
        String username = null;
        Claims claims = getClaimsFromToken(token);
        username = claims.getSubject();
        return username;
    }
    
    /**
     * 验证token是否有效
     * */
    public boolean validateToken(String token, UserDetails userDetails){
        return false;
    }
    
    /**
     * 验证token是否已经失效
     * */
    public boolean isTokenExpired(String token){
        Date date = getExpiredDateFromToken(token);
        return date.before(new Date());
    }
    
    /**
     * 从token获取过期日期
     * */
    public Date getExpiredDateFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
    
    /**
     * 
     * 根据用户信息生成token
     * */
    public String generateToken(UserDetails userDetails){
        Map<String ,Object> claims = new HashMap<String,Object>();
        claims.put("CLAIM_KEY_USERNAME",userDetails.getUsername());
        claims.put("CLAIM_KEY_CREATEN",new Date());
        return generateToken(claims);
    }
    
    /**
     * 根据用户名生成token
     * 
     * */
    public String generateToken(String username){
        Map<String ,Object> claims = new HashMap<String,Object>();
        claims.put("CLAIM_KEY_USERNAME",username);
        claims.put("CLAIM_KEY_CREATEN",new Date());
        return generateToken(claims);
    }
    
}
