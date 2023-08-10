package org.example.filter;

import io.jsonwebtoken.Claims;
import org.example.model.User;
import org.example.service.JWTService;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "securityFilter", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SecurityFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    private static final Set<String> ALLOWED_PATH = new HashSet<>(Arrays.asList("/login"));
    private static final Set<String> IGNORED_PATH = new HashSet<>(Arrays.asList("/auth"));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        int statusCode = authorization(request);

        if (statusCode == HttpServletResponse.SC_ACCEPTED) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).sendError(statusCode);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private int authorization(HttpServletRequest req) {
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        String url = req.getRequestURI();
        if (IGNORED_PATH.contains(url)) {
            return HttpServletResponse.SC_ACCEPTED;
        }

        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?)", "");
            if (token == null || token.isEmpty()) {
                return statusCode;
            }

            Claims claims = jwtService.decryptToken(token);

            if (claims.getId() != null) {
                User user = userService.getById(Long.valueOf(claims.getId()));
                if (user != null) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                }
            }
        } catch (Exception e) {
            logger.info("can't get token");
        }
        return statusCode;

    }
}
