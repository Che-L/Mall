package com.project.mall.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
 * JWT Token 生成与解析工具。
 * JWT 格式：header.payload.signature
 * <p>header（算法、类型）示例：{"alg": "HS512","typ": "JWT"}
 * <p>payload（用户名、创建时间、过期时间等）示例：{"sub":"wang","created":1489079981393,"exp":1489684781}
 * <p>signature：HMACSHA512(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)
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

    /** 根据 claims 生成 JWT */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /** 从 token 中解析 Claims */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT 格式校验失败:{}", token);
        }
        return claims;
    }

    /** 计算 token 过期时间 */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /** 从 token 中取登录用户名 */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 校验 token 是否仍有效。
     *
     * @param token       客户端传入的 token
     * @param userDetails 当前用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /** 是否已过期 */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /** 从 token 取过期时间 */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /** 根据 {@link UserDetails} 生成 token */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 原 token 未过期时可刷新。
     *
     * @param oldToken 带 tokenHead 前缀的完整串
     */
    public String refreshHeadToken(String oldToken) {
        if(StrUtil.isEmpty(oldToken)){
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if(StrUtil.isEmpty(token)){
            return null;
        }
        // 解析失败
        Claims claims = getClaimsFromToken(token);
        if(claims==null){
            return null;
        }
        // 已过期不允许刷新
        if(isTokenExpired(token)){
            return null;
        }
        // 30 分钟内刚刷新过则返回原 token
        if(tokenRefreshJustBefore(token,30*60)){
            return token;
        }else{
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断在 time 秒窗口内是否刚刷新过。
     *
     * @param token 原 token
     * @param time  时间窗口（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        if(refreshDate.after(created)&&refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }
}
