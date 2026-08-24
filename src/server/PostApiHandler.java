package server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.SimpleHttpServer;

public class PostApiHandler implements HttpHandler {


    private static final List<Post> postList = new ArrayList<>();
    private static int nextId = 1;

    static {
        addPost(new Post("포스트1", "포스트1 테스트입니다."));
        addPost(new Post("ㅎㅇ요", "그래 형이야."));
    }

    private static synchronized int addPost(Post post) {
        post.setPageId(nextId);
        nextId++;
        postList.add(post);
        return post.getPageId();
    }

    private ArrayList<Post> copyPostList() {
        return new ArrayList<>(postList);
    }

    private void readPosts(HttpExchange exchange) throws IOException {
        SimpleHttpServer.sendJson(exchange, 200, copyPostList());
    }

    private void createPost(HttpExchange exchange) throws IOException {
        String requestBody = SimpleHttpServer.readRequestBody(exchange);
        String path = exchange.getRequestURI().getPath();

        Post post;

        try {
            post = new Gson().fromJson(requestBody, Post.class);

            if (post == null ||
                    post.getTitle() == null ||
                    post.getTitle().isBlank() ||
                    post.getContent() == null ||
                    post.getContent().isBlank()) {
                SimpleHttpServer.sendResponse(exchange, 400, SimpleHttpServer.TYPE_TEXT, "제목과 본문은 필수입니다.");
                return;
            }

            // post.setUploadedAt(LocalDateTime.now());

            addPost(post);
            SimpleHttpServer.sendJson(exchange, 201, post);
        } catch (JsonSyntaxException e) {
            SimpleHttpServer.sendResponse(exchange, 400, SimpleHttpServer.TYPE_TEXT, "올바르지 않은 Json 형식");
            return;
        }

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            readPosts(exchange);
        } else if (method.equals("POST")) {
            createPost(exchange);
        }
    }
}
