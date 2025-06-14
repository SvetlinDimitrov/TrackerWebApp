package org.gateway.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

  @Bean
  CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    String clientHost = "localhost";
    String hosts = String.format("http://%s:[*]", clientHost);
    corsConfig.setAllowedOriginPatterns(List.of(hosts));
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");

    org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source =
        new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }
}
