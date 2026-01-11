package com.ticketsystem.security.filter;
import com.ticketsystem.security.auth.JwtAuthentication;
import com.ticketsystem.security.auth.JwtUtil;
import com.ticketsystem.security.auth.UserPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws java.io.IOException, jakarta.servlet.ServletException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            Long userId = JwtUtil.extractUserId(token);

            UserPrincipal principal = new UserPrincipal(userId);

            JwtAuthentication authentication =
                    new JwtAuthentication(principal);

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}