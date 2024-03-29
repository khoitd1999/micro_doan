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
                        .path("/product/**")
                        .uri("lb://product-module"))
                .route(p -> p
                        .path("/login/**")
                        .uri("lb://user-module"))
                .route(p -> p
                        .path("/warehouse/**")
                        .uri("lb://common-module"))
                .route(p -> p
                        .path("/policy/**")
                        .uri("lb://common-module"))
                .route(p -> p
                        .path("/bill/**")
                        .uri("lb://order-module"))
                .route(p -> p
                        .path("/cart/**")
                        .uri("lb://order-module"))
                .route(p -> p
                        .path("/inventory/**")
                        .uri("lb://order-module"))
                .route(p -> p
                        .path("/warehousereceipt/**")
                        .uri("lb://order-module"))
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
