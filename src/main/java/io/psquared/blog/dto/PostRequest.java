package io.psquared.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequest {
    @NotBlank(message = "title is required")
    @Size(min = 2, message = "title must be greater than 2 chars")
    private String title;

    @NotBlank(message = "content is required")
    @Size(min = 2, message = "content must be greater than 2 chars")
    private String content;

    @NotBlank(message = "category is required")
    private String category;

    public PostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
