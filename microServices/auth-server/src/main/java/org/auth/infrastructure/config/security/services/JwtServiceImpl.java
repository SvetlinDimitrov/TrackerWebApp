package org.auth.infrastructure.config.security.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.auth.features.user.entity.User;
import org.auth.infrastructure.config.security.config.JwtConfig;
import org.auth.infrastructure.config.security.dto.AccessTokenView;
import org.auth.infrastructure.config.security.dto.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  private final JwtConfig jwtConfig;

  public AuthenticationResponse generateToken(User user) {
    var accessTokenView = generateAccessToken(user);

    return new AuthenticationResponse(accessTokenView);
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

  public String getEmailFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(jwtConfig.getSecretKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  private AccessTokenView generateAccessToken(User user) {
    var currentDate = new Date();
    var expirationDate = new Date(currentDate.getTime() + jwtConfig.getJwtDuration());

    String token =
        Jwts.builder()
            .setSubject(user.getEmail())
            .claim("id", user.getId())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .claim("kilograms", user.getKilograms())
            .claim("height", user.getHeight())
            .claim("workoutState", user.getWorkoutState())
            .claim("gender", user.getGender())
            .claim("age", user.getAge())
            .claim("role", user.getRole())
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .signWith(jwtConfig.getSecretKey(), SignatureAlgorithm.HS256)
            .compact();

    return new AccessTokenView(token);
  }
}
