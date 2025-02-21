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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.user.dto.UserView;
import org.example.domain.user.enums.Gender;
import org.example.domain.user.enums.UserDetails;
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

    String workoutStateStr = getClaimValue(claims, "workoutState", String.class);
    WorkoutState workoutState =
        workoutStateStr != null ? WorkoutState.valueOf(workoutStateStr) : null;
    String genderStr = getClaimValue(claims, "gender", String.class);
    Gender gender = genderStr != null ? Gender.valueOf(genderStr) : null;
    String userDetailsStr = getClaimValue(claims, "userDetails", String.class);
    UserDetails userDetails =
        userDetailsStr != null ? UserDetails.valueOf(userDetailsStr) : null;
    String role = getClaimValue(claims, "role", String.class);
    UserRole userRole = role != null ? UserRole.valueOf(role) : null;

    return Optional.of(new UserView(
        getClaimValue(claims, "id", String.class),
        getClaimValue(claims, "username", String.class),
        getClaimValue(claims, "email", String.class),
        getClaimValue(claims, "kilograms", Double.class),
        getClaimValue(claims, "height", Double.class),
        workoutState,
        gender,
        userDetails,
        userRole,
        getClaimValue(claims, "age", Integer.class)
    ));
  }

  private Jws<Claims> decodeToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfig.getSecretKey())
        .build()
        .parseClaimsJws(token);
  }

  public Boolean isAccessTokenValid(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(jwtConfig.getSecretKey())
          .build()
          .parseClaimsJws(token);
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
    return claims.containsKey(key) ? claims.get(key, clazz) : null;
  }
}