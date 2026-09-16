package api_gateway.api.Exception;

import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
@Component
@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @PostConstruct
    public void init() {
        System.out.println("===== GLOBAL EXCEPTION HANDLER CREATED =====");
    }


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        var response = exchange.getResponse();

        System.out.println("GLOBAL HANDLER CALLED: " + ex.getMessage());

        response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String body = """
                {
                    "status": 503,
                    "message": "Service temporarily unavailable"
                }
                """;

        var buffer = response.bufferFactory()
                .wrap(body.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}