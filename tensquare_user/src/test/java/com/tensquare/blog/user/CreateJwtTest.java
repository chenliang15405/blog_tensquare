package com.tensquare.blog.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @auther alan.chen
 * @time 2019/6/7 5:38 PM
 */
public class CreateJwtTest {

    @Test
    public void createJwt() {
        JwtBuilder jwt = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())    //签发时间
                .signWith(SignatureAlgorithm.HS256, "tangsong"); // 签名

        System.out.println(jwt.compact());
    }

    @Test
    public void parseJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTk5MDA1MDB9.ECFA1OhlrhEV24jHDjP-sIty1PBinV5n95r__IdjoSw";

        Claims claims = Jwts.parser().setSigningKey("tangsong").parseClaimsJws(token).getBody();

        System.out.println("id" + claims.getId());
        System.out.println("subject " + claims.getSubject());
        System.out.println("issueAt " + claims.getIssuedAt());

    }

    @Test
    public void createExpireJwt() {
        long now = System.currentTimeMillis();
        long exp = now + 1000*60;
        JwtBuilder jwt = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())    //签发时间
                .signWith(SignatureAlgorithm.HS256, "tangsong") // 签名
                .setExpiration(new Date(exp)); // 设置过期时间

        System.out.println(jwt.compact());
    }

    @Test
    public void parseExpireJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTk5MDA5MTQsImV4cCI6MTU1OTkwMDk3NH0.7mtSLsnbPUf_Il_8ZgZZV5_GqGNJS3pNfhFEE9f2YyA";

        Claims claims = Jwts.parser().setSigningKey("tangsong").parseClaimsJws(token).getBody();

        System.out.println("id" + claims.getId());
        System.out.println("subject " + claims.getSubject());
        System.out.println("issueAt " + claims.getIssuedAt());

        System.out.println("过期时间" + claims.getExpiration());

    }


    @Test
    public void createCustomJwt() {
        long now = System.currentTimeMillis();
        long exp = now + 1000*60;
        JwtBuilder jwt = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())    //签发时间
                .signWith(SignatureAlgorithm.HS256, "tangsong") // 签名
                .setExpiration(new Date(exp)) // 设置过期时间
                .claim("roles", "admin")    //向token中存入自定义的属性
                .claim("logo", "logo.png");

        System.out.println(jwt.compact());
    }


    @Test
    public void parseCustomJwt() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NTk5MDExNDksImV4cCI6MTU1OTkwMTIwOSwicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJsb2dvLnBuZyJ9.gEGehkBUzZHFffZVVJ728lEMOo-WJag-8NzDZJBfaBg";

        Claims claims = Jwts.parser().setSigningKey("tangsong").parseClaimsJws(token).getBody();

        System.out.println("自定义role : " + claims.get("roles"));
        System.out.println("自定义logo :" + claims.get("logo"));
    }
}
