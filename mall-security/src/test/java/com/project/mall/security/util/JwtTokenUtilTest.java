package com.project.mall.security.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JWT 工具类单元测试（通过反射注入密钥与过期时间，不启动完整 Spring 容器）。
 */
class JwtTokenUtilTest {

    /**
     * JJWT 将字符串密钥按 Base64 解码；64 字节原始密钥经编码后满足 HS512（至少 512 bit）。
     */
    private static final String SECRET;

    static {
        byte[] raw = new byte[64];
        Arrays.fill(raw, (byte) 9);
        SECRET = Base64.getEncoder().encodeToString(raw);
    }
    private static final String TOKEN_HEAD = "Bearer ";

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", SECRET);
        ReflectionTestUtils.setField(jwtTokenUtil, "expiration", 3600L);
        ReflectionTestUtils.setField(jwtTokenUtil, "tokenHead", TOKEN_HEAD);
    }

    private static UserDetails user(String name) {
        return new User(name, "pw", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void generateTokenThenParseUsername() {
        UserDetails u = user("alice");
        String token = jwtTokenUtil.generateToken(u);
        assertNotNull(token);
        assertEquals("alice", jwtTokenUtil.getUserNameFromToken(token));
    }

    @Test
    void validateTokenTrueWhenSubjectMatchesUserDetails() {
        UserDetails u = user("bob");
        String token = jwtTokenUtil.generateToken(u);
        assertTrue(jwtTokenUtil.validateToken(token, u));
    }

    @Test
    void validateTokenFalseWhenSubjectDiffersFromUserDetails() {
        UserDetails owner = user("carol");
        UserDetails other = user("dave");
        String token = jwtTokenUtil.generateToken(owner);
        assertFalse(jwtTokenUtil.validateToken(token, other));
    }

    @Test
    void refreshHeadTokenReturnsNullForBlankInput() {
        assertNull(jwtTokenUtil.refreshHeadToken(null));
        assertNull(jwtTokenUtil.refreshHeadToken(""));
    }

    @Test
    void refreshHeadTokenReturnsNullForMalformedJwt() {
        assertNull(jwtTokenUtil.refreshHeadToken(TOKEN_HEAD + "not-a-jwt"));
    }

    @Test
    void refreshHeadTokenReturnsJwtForValidPrefixedToken() {
        UserDetails u = user("erin");
        String token = jwtTokenUtil.generateToken(u);
        String refreshed = jwtTokenUtil.refreshHeadToken(TOKEN_HEAD + token);
        assertNotNull(refreshed);
    }
}
