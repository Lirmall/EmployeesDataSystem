package ru.klokov.employeesdatasystem.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.klokov.employeesdatasystem.security.services.SecurityUserDetailsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Service
public class TokenAuthenticationService {
    private static final String SECRET = "Secret";
    private static final long EXPIRATION_TIME = 864000000; //10 days in milliseconds
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private static Logger LOG = LoggerFactory.getLogger(TokenAuthenticationService.class);

    private static SecurityUserDetailsManager securityUserDetailsManager;

    @Autowired
    public TokenAuthenticationService(SecurityUserDetailsManager securityUserDetailsManager) {
        this.securityUserDetailsManager = securityUserDetailsManager;
    }

    static void addAuthentication(HttpServletResponse response, String username) {
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + generateToken(username));
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = getToken(request);

        if(!hasText(token)) {
            return null;
        }

        /*проверка наличия сессии по токену - можно добавить*/

        String userName = getUsernameFromToken(token);

        UserDetails user = securityUserDetailsManager.loadUserByUsername(userName);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return authentication;
    }

    private static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private static String getToken(HttpServletRequest request) {
        if(request.getHeader(HEADER_STRING) != null) {
            String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
            String tokenWithoutSpaces = token.replace(" ", "");
            return tokenWithoutSpaces;
        } else {
            return null;
        }
    }

    public static String getUsernameFromToken(String token) {
        try {
            return token != null
                    ? Jwts.parser().setSigningKey(SECRET)
                      .parseClaimsJws(token)
                      .getBody()
                      .getSubject()
                    : null;
        } catch (JwtException e) {
            LOG.info("Ошибка обработки токена: {}", token);
            return null;
        }
    }
}
