package com.example.Gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));  //Cấu hình kết nối đến Redis Server
    }

    @Bean
    public RouteLocator myRoute(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/users/**")
                        .filters(f -> f.requestRateLimiter().configure(c -> c.setRateLimiter(redisRateLimiter()).setStatusCode(HttpStatus.TOO_MANY_REQUESTS)))
                        .uri("lb://users-service"))
                .route(p -> p
                        .path("/comment/**")
                        .uri("lb://comment-service"))
                .build();
    }

//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just("1");
//    }

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 2, 1);
    }
}
