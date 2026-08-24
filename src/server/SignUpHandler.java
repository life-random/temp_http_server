package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class SignUpHandler implements HttpHandler {
    private final String SIGN_UP_PAGE = "html/sign_up.html";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream out = null;

        try {
            // / <-- 이 경로를 처리하는 핸들러
            // 주의할 점
            // "/" 로 등록한 핸들러는 다른 핸들러가 맡지 않은 "모든" 경로를 받아요
            // 그래서 정확히 "/" 인지 직접 확인하고, 아니면 404를 돌려 줘야 한다.
            String path = exchange.getRequestURI().getPath();
            if (!path.equals("/api/sign_up")) {
                SimpleHttpServer.sendResponse(exchange, 404,
                        SimpleHttpServer.TYPE_TEXT, "404 Not Found : " + path);
                return;
            }
            Path paths = Paths.get(SIGN_UP_PAGE);

            Files.readString(paths);
            String html = Files.readString(paths);

            byte[] bytes = html.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders()
                    .set("Content-Type", "text/html; charset=UTF-8");

            exchange.sendResponseHeaders(200, bytes.length);

            out = exchange.getResponseBody();
            out.write(bytes);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            exchange.close();
            Objects.requireNonNull(out).close();
        }
    }
}
