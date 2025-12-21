package com.noteam.next.filter;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.noteam.next.models.User;
import com.noteam.next.services.UserService;
import com.noteam.next.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private static Logger log = Logger.getLogger(SecurityFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Check Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String email = jwtUtil.getEmailFromToken(token);
            Optional<User> opUser = userService.findByEmail(email);
            if (opUser.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            User user = opUser.get();
            log.info("SECURITY: " + user.getEmail());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception e) {
            log.severe("ERROR in Auth");
        }

        filterChain.doFilter(request, response);
    }
}
