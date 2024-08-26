package com.example.demo.security;

import com.example.demo.users.domain.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final SecretKey secretKey;

    public JwtRequestFilter(CustomUserDetailsService customUserDetailsService, @Value("${jwt.secret.key}") String secretKey) {
        this.customUserDetailsService = customUserDetailsService;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // GET '/api/users/{id}'에서 id 값을 파싱
        //Long id = parseUserId(request);

        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        Long userId = null;
        String username = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = extractUserId(jwt);
            username = extractUsername(jwt);
//            role = extractRole(jwt);
        }

        boolean condition = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication() == null;
        log.info("Condition is true? {}", condition);
        if (username != null && SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            Long id = userDetails.getId();
            boolean jwtValid = validateToken(jwt, id);
            if (jwtValid) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    private Long parseUserId(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // URL이 /api/users/{id} 형태라고 가정하고 파싱
        String[] uriParts = requestURI.split("/");

        Long id = null;
        if (uriParts.length > 3 && uriParts[2].equals("users")) {
            try {
                id = Long.parseLong(uriParts[3]);
                // id를 이용한 추가 로직 작성 가능
                System.out.println("Extracted ID: " + id);
            } catch (NumberFormatException e) {
                // id가 올바른 형식이 아닐 경우 예외 처리
                System.err.println("Invalid ID format");
            }
        }

        return id;
    }

    /**
     *  ("Bearer "을 제거한) JWT accessToken으로부터 user_id를 추출
     */
    private Long extractUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("user_id", Long.class);
    }

    /**
     *  ("Bearer "을 제거한) JWT accessToken으로부터 username을 추출
     */
    private String extractUsername(String token) {
        Claims claims = getClaims(token);
        return claims.get("username", String.class);
    }

    /**
     *  ("Bearer "을 제거한) JWT accessToken으로부터 roles를 추출
     */
    private String extractRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class);
    }

    /**
     *  ("Bearer "을 제거한) JWT accessToken으로부터 user_id를 추출하고 api/users/{id}의 id 값과 비교하여 토큰의 유효성을 검증
     */
    private boolean validateToken(String token, Long id) {
        Long userId = extractUserId(token);
        return userId.equals(id) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // secretKey를 이용하여 복호화
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}