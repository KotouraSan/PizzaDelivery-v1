package uz.ksan.backend.pizzadelivery.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.ksan.backend.pizzadelivery.dto.JwtAuthenticationDto;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    private static final Logger LOGGER = LogManager.getLogger(JwtService.class);
    @Value("cfd54b15acfe8b44f7f0cdad5eeaec47d1895e64c33e6bbddcb2ee6b4a1dbd4b")      //секретный ключ, generate jwt secret
    private String jwtSecret;

    public JwtAuthenticationDto generateAuthToken(String username) {            //генерация основого токена
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();           //валидный 1 минуту, зачем используя
        jwtDto.setToken(generateJwtToken(username));                  //refresh токен, обновить основной
//        jwtDto.setRefreshToken(generateRefreshToken(username));
        return jwtDto;
    }

    public JwtAuthenticationDto refreshBaseToken(String username, String refreshToken) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setToken(generateJwtToken(username));
//        jwtDto.setRefreshToken(refreshToken);                       //тот самый рефреш токен
        return jwtDto;
    }

    public String getUsernameFromToken(String token) {          //получение username из зашифрованного токена
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch (ExpiredJwtException expEx) {
            LOGGER.error("Expired JwtException", expEx);
        }catch (UnsupportedJwtException expEx) {
            LOGGER.error("Unsupported JwtException", expEx);
        }catch (MalformedJwtException expEx) {
            LOGGER.error("Malformed JwtException", expEx);
        }catch (SecurityException expEx) {
            LOGGER.error("Security Exception", expEx);
        }catch (Exception expEx) {
            LOGGER.error("Invalid token", expEx);
        }
        return false;

    }

    private String generateJwtToken(String username) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(60).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(username)
                .expiration(date)               //генерация токена посе ввода логина и пароля
                .signWith(getSignKey())
                .compact();
    }

    private String generateRefreshToken(String username) {
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(username)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {                        //уникальная подпись
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}


