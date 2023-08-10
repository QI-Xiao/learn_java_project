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

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JWTService jwtService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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

        String verb = req.getMethod();

        try {
            String token = req.getHeader("Authorization").replaceAll("^(.*?)", "");
            if (token == null || token.isEmpty()) {
                return statusCode;
            }

            Claims claims = jwtService.decryptToken(token);

            if (claims.getId() != null) {
                User user = userService.getById(Long.valueOf(claims.getId()));
                if (user == null) {
                    return statusCode;
                }
            }

            logger.info("claims {}", claims);

            String allowedResources = "";
            switch (verb) {
                case "POST": allowedResources = (String) claims.get("allowedCreateResources"); break;
                case "PUT":
                case "PATCH":
                    allowedResources = (String) claims.get("allowedUpdateResources"); break;
                case "DELETE": allowedResources = (String) claims.get("allowedDeleteResources"); break;
                case "GET":
                default: allowedResources = (String) claims.get("allowedResources"); break;
            }

            logger.info("verb {}", verb);
            logger.info("method {}", allowedResources);
            logger.info("url {}", url);

            for(String s : allowedResources.split(",")) {
                String url_trim = url.trim().toLowerCase();
                String s_trim = s.trim().toLowerCase();
                logger.info("url - {}; s - {}; {} - {}", url_trim, "\"" + s_trim + "\"", url_trim.startsWith(s_trim), !s_trim.equals(""));
                if(!s_trim.equals("") && url_trim.startsWith(s_trim)) {
                    statusCode = HttpServletResponse.SC_ACCEPTED;
                    logger.info("change the statusCode {}", statusCode);
                    break;
                }
            }
        } catch (Exception e) {
            logger.info("can't get token");
        }
        logger.info("the statusCode {}", statusCode);
        return statusCode;
    }
}
