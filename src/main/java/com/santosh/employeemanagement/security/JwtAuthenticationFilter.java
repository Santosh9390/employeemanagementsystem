package com.santosh.employeemanagement.security;

import com.santosh.employeemanagement.service.UserService;
import com.santosh.employeemanagement.util.JwtUtil;
import com.santosh.employeemanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
        }

        // If username is found and context doesn't already have authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<User> optionalUser = userService.findByUsername(username);

            if (optionalUser.isPresent() && jwtUtil.validateToken(token, username)) {
                User user = optionalUser.get();
                // ✅ Create a list with one authority based on the role from the user
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole()); // e.g., "ROLE_USER"

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user, null, Collections.singletonList(authority))
                ;

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication to context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
