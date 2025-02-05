package com.example.board.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${spring.jwt.access-secret}")
    private String accessSecret;

    @Value("${spring.jwt.refresh-secret}")
    private String refreshSecret;

    @Value("${spring.jwt.access-expire-time}")
    private int accessExpireTime;

    @Value("${spring.jwt.refresh-expire-time}")
    private int refreshExpireTime;


    private SecretKey accessKey;
    private SecretKey refreshKey;

    @PostConstruct
    public void init() {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret));
    }

    // 액세스 토큰, 리프레시 토큰 생성
    public JwtTokenInfoDto createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Date now = new Date();
        Date accessExpire = new Date(now.getTime() + accessExpireTime);
        Date refreshExpire = new Date(now.getTime() + refreshExpireTime);

        String accessToken = Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(accessExpire)
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();

        String refeshToken = Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(refreshExpire)
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();

        return new JwtTokenInfoDto("Bearer", accessToken, refeshToken);
    }

    // Authentication 추출
    public Authentication getAuthentication(String token) {
        Claims claims = getAccessTokenClaims(token);
        Object auth = claims.get("auth");
        if(auth == null) {
            throw new RuntimeException("Invalid token");
        }

        List<SimpleGrantedAuthority> authorities = Arrays.stream(auth.toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
    }

    // 리프레시 토큰 검증 및 새로운 액세스 토큰 생성
    public JwtTokenInfoDto validateRefreshTokenAndCreateAccessToken(String refreshToken) {
        Claims refreshClaims = getRefreshTokenClaims(refreshToken);
        Date now = new Date();

        String newAccessToken = Jwts.builder()
                .setSubject(refreshClaims.getSubject())
                .claim("auth", refreshClaims.get("auth"))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessExpireTime))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();

        return new JwtTokenInfoDto("Bearer", newAccessToken, refreshToken);
    }

    // 액세스토큰 검증(필터 용도)
    public boolean validateAccessToken(String token) {
        try {
            getAccessTokenClaims(token);
            return true;
        } catch (Exception e) {
            log.info("validateAccessToken " + e.getMessage());
            return false;
        }
    }


    private Claims getAccessTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims getRefreshTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
