//package shop.project.venver_gate_way.filters;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class SecurityGlobalFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
//        System.out.println("Global pre-filter executed");
//
////        ServerHttpRequest request = exchange.getRequest();
////
////        request.mutate()
////                .header()
//
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            System.out.println("Global post-filter executed");
//        }));
//    }
//
//    @Override
//    public int getOrder(){
//        return -1;
//    }
//
//}
