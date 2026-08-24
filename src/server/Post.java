package server;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int pageId;
    private String title;
    private String content;
    // private LocalDateTime uploadedAt;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
}
