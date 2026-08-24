package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SignUpProcesHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("POST".equalsIgnoreCase(method)) {

            InputStream inputStream = exchange.getRequestBody();

            String body = new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

            System.out.println(body);
        }

        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseBody().close();
    }
}
