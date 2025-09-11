package agriconnect.agriconnect.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;

public class JwtTokenValidator extends OncePerRequestFilter {

    // ✅ Centralized public endpoints (with /api prefix)
    private static final String[] PUBLIC_ENDPOINTS = {
            "/api/login",
            "/api/signup",
            "/api/forgot-password",
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources",
            "/webjars",
            "/swagger-ui.html",
            "/favicon.ico"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getServletPath();

        // ✅ Skip filter for public endpoints
        for (String path : PUBLIC_ENDPOINTS) {
            if (uri.startsWith(path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // ✅ Extract JWT from header
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt == null || !jwt.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or malformed Authorization header");
            return;
        }

        jwt = jwt.substring(7); // remove "Bearer "

        try {
            SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(JwtConstant.SECRET_KEY));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();

            // ✅ No roles, just authenticate with username
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
