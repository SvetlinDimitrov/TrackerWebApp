package org.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.user.dto.UserView;
import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.UserRole;
import org.example.domain.user.enums.WorkoutState;
import org.gateway.config.JwtConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

  private final JwtConfig jwtConfig;

  public Optional<UserView> verifyAndExtractUser(HttpHeaders headers) {
    List<String> authHeader = headers.get(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || authHeader.isEmpty()) {
      return Optional.empty();
    }

    String token = authHeader.get(0);
    token = token.substring(7);

    if (!isAccessTokenValid(token)) {
      return Optional.empty();
    }

    Jws<Claims> claimsJws = decodeToken(token);
    Claims claims = claimsJws.getBody();

    WorkoutState workoutState =
        WorkoutState.valueOf(getClaimValue(claims, "workoutState", String.class));
    Gender gender = Gender.valueOf(getClaimValue(claims, "gender", String.class));
    UserRole userRole = UserRole.valueOf(getClaimValue(claims, "role", String.class));

    return Optional.of(
        new UserView(
            getClaimValue(claims, "id", UUID.class),
            getClaimValue(claims, "username", String.class),
            getClaimValue(claims, "email", String.class),
            getClaimValue(claims, "kilograms", Double.class),
            getClaimValue(claims, "height", Double.class),
            workoutState,
            gender,
            userRole,
            getClaimValue(claims, "age", Integer.class)));
  }

  private Jws<Claims> decodeToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfig.getSecretKey())
        .build()
        .parseClaimsJws(token);
  }

  public Boolean isAccessTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(jwtConfig.getSecretKey()).build().parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("JWT token is malformed: {}", e.getMessage());
    } catch (SignatureException e) {
      log.error("JWT token signature validation failed: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT token is illegal or inappropriate: {}", e.getMessage());
    }
    return false;
  }

  private <T> T getClaimValue(Claims claims, String key, Class<T> clazz) {
    if (!claims.containsKey(key)) {
      return null;
    }
    Object claim = claims.get(key);

    if (clazz == UUID.class && claim instanceof String) {
      try {
        // Only for UUID conversion
        return clazz.cast(UUID.fromString((String) claim));
      } catch (IllegalArgumentException e) {
        log.error("Failed to convert claim '{}' to UUID: {}", key, e.getMessage());
        return null;
      }
    }
    return clazz.cast(claim);
  }
}
