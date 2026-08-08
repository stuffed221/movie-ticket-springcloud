package org.example.filter;

import org.example.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 放行 OPTIONS 预检请求（跨域）
        if ("OPTIONS".equalsIgnoreCase(exchange.getRequest().getMethodValue())) {
            return chain.filter(exchange);
        }

        // 白名单：登录/注册/浏览类接口直接放行,取代WebMvcConfig
        if (path.startsWith("/user/login")
                || path.startsWith("/user/insert")
                || path.startsWith("/user/count")
                || path.startsWith("/manager/login")
                || path.startsWith("/manager/logout")
                || path.startsWith("/movie/page")
                || path.startsWith("/movie/listall")
                || path.startsWith("/movie/count")
                || path.startsWith("/schedule/list")
                || path.startsWith("/cinema/list")
                || path.startsWith("/order/list")
                || path.startsWith("/order/count")) {
            return chain.filter(exchange);
        }

        // 取 token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        // 校验 JWT
        try {
            token = token.substring(7);
            JwtUtils.parseToken(token);
            return chain.filter(exchange);
        } catch (Exception e) {
            return unauthorized(exchange);
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
