package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MentHandler implements HttpHandler {

    private static final String[] MENT_LIST = {
            "삶이 있는 한 희망은 있다.",
            "평생 살 것처럼 꿈을 꾸어라. 그리고 내일 죽을 것처럼 오늘을 살아라.",
            "가장 큰 영광은 한 번도 실패하지 않는 것이 아니라, 실패할 때마다 다시 일어나는 데 있다.",
            "행복은 목적지가 아니라 우리가 여행하는 과정이다.",
            "당신이 할 수 있다고 믿든 할 수 없다고 믿든, 당신의 믿음대로 될 것이다.",
            "상처받은 마음을 치유하는 가장 강력한 약은 사랑과 다정함이다.",
            "멀리 가고 싶다면 함께 가라.",
            "당신이 걷는 길에 장애물이 없다면, 그 길은 아마 어디로도 인도하지 않을 것이다.",
            "잔잔한 바다는 결코 유능한 뱃사공을 만들 수 없다.",
            "다른 사람의 속도에 맞추느라 당신의 소중한 계절을 놓치지 말라.",
    };

    private final Random random = new Random();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();

            if (method.equals("GET")) {
                handleGet(exchange);
            } else {
                exchange.getResponseHeaders().set("Allow", "GET");
                SimpleHttpServer.sendResponse(exchange, 405, SimpleHttpServer.TYPE_TEXT, "지원하지 않는 메서드 방식입니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        int randomIndex = random.nextInt(MENT_LIST.length);
        String randomMent = MENT_LIST[randomIndex];

        Map<String, Object> result = new HashMap<>();
        result.put("오늘의 명언 : ", randomMent);

        SimpleHttpServer.sendJson(exchange, 200, result);
    }
}
