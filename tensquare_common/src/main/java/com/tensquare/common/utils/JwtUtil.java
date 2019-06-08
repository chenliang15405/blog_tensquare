package com.tensquare.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * Jwt生成工具类，在配置文件中指定盐和过期时间，过期时间可以省略
 */
@ConfigurationProperties("jwt.config")
public class JwtUtil {

    private String key ;    //签名

    private long ttl ;  //过期时间，可以不设置

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }


    // 角色的key
    private static final String ROLE_CLAIMS = "role";

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";


    /**
     * 生成JWT
     *
     * @param id
     * @param subject
     * @return
     */
    public String createJWT(String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)      //设置id
                .setSubject(subject)    // 名称
                .setIssuedAt(now)       // 签发时间
                .signWith(SignatureAlgorithm.HS256, key)    //设置签名
                .claim(ROLE_CLAIMS, roles); //设置自定义属性
        if (ttl > 0) {
            builder.setExpiration( new Date( nowMillis + ttl)); //设置过期时间
        }
        return builder.compact();
    }

    /**
     * 生成jwt token，签名是通过参数传递
     * @param id
     * @param subject
     * @param roles
     * @return
     */
    public String createJWTWithParamsKey(String key, long ttl, String id, String subject, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(id)      //设置id
                .setSubject(subject)    // 名称
                .setIssuedAt(now)       // 签发时间
                .signWith(SignatureAlgorithm.HS256, key)    //设置签名
                .claim(ROLE_CLAIMS, roles); //设置自定义属性
        if (ttl > 0) {
            builder.setExpiration( new Date( nowMillis + ttl)); //设置过期时间
        }
        return builder.compact();
    }

    /**
     * 解析JWT 通过参数中的签名
     * @param jwtStr
     * @return
     */
    public Claims parseJWTWithParamsKey(String key, String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

    /**
     * 解析JWT
     * @param jwtStr
     * @return
     */
    public Claims parseJWT(String jwtStr){
        return  Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

    /**
     * 从token中获取登录名
     * @param token
     * @return
     */
    public String getUsername(String token){
        return parseJWT(token).getSubject();
    }

    /**
     * 从token中获取登录名
     * @param token
     * @return
     */
    public String getUsernameWithSecurityFilter(String key, String token){
        return parseJWTWithParamsKey(key, token).getSubject();
    }

    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public String getUserRole(String token){
        return (String) parseJWT(token).get(ROLE_CLAIMS);
    }


    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public String getUserRoleWithSecurityFilter(String key, String token){
        return (String) parseJWTWithParamsKey(key,token).get(ROLE_CLAIMS);
    }


}