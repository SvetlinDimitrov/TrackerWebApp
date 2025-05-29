package org.gateway.utils;

import org.example.util.GsonWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class HttpResponseUtil {

  private static final GsonWrapper GSON_WRAPPER = new GsonWrapper();

  public static Mono<Void> writeJsonResponse(
      ServerWebExchange exchange, HttpStatus status, ProblemDetail problemDetail) {
    exchange.getResponse().setStatusCode(status);
    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

    byte[] bytes = GSON_WRAPPER.toJson(problemDetail).getBytes();
    return exchange
        .getResponse()
        .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
  }

  public static ProblemDetail createProblemDetail(HttpStatus status, String title, String detail) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
    problemDetail.setTitle(title);
    return problemDetail;
  }
}
