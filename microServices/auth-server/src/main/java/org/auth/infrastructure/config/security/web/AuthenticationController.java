package org.auth.infrastructure.config.security.web;

import static org.auth.infrastructure.exceptions.ExceptionMessages.INVALID_USERNAME_OR_PASSWORD;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.auth.features.user.services.UserService;
import org.auth.infrastructure.config.security.dto.AuthenticationRequest;
import org.auth.infrastructure.config.security.dto.AuthenticationResponse;
import org.auth.infrastructure.config.security.services.JwtService;
import org.example.domain.user.paths.AuthenticationControllerPaths;
import org.example.exceptions.throwable.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AuthenticationControllerPaths.BASE)
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @PostMapping(AuthenticationControllerPaths.LOGIN)
  public ResponseEntity<AuthenticationResponse> create(
      @Valid @RequestBody AuthenticationRequest authenticationRequest) {
    var user = userService.findByEmail(authenticationRequest.email());

    if (passwordEncoder.matches(authenticationRequest.password(), user.getPassword())) {
      return ResponseEntity.ok().body(jwtService.generateToken(user));
    }
    throw new BadRequestException(INVALID_USERNAME_OR_PASSWORD);
  }
}
