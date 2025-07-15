package com.sekolah.project_sekolah.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.validateToken(token);
                String email = claims.getSubject();
                String role = claims.get("role", String.class); // ambil role dari token
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // konversi role menjadi authority
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
                            null, List.of(authority));

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token tidak valid");
                System.out.println("JWT Error: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
