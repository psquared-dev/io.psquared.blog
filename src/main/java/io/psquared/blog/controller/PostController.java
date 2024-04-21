package io.psquared.blog.controller;

import io.psquared.blog.dto.PostPatchRequest;
import io.psquared.blog.dto.PostRequest;
import io.psquared.blog.dto.PostResponse;
import io.psquared.blog.entity.Post;
import io.psquared.blog.exceptions.NotFound;
import io.psquared.blog.mapper.PostMapper;
import io.psquared.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@Valid @RequestBody PostRequest postRequest) {
        Post post = postService.ceatePost(postRequest);
        return PostMapper.INSTANCE.toResponse(post);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable long id) {
        Post post = postService
                .getPost(id)
                .orElseThrow(() -> new NotFound("Not found post with id: " + id));

        return PostMapper.INSTANCE.toResponse(post);
    }

    @PutMapping("/{id}")
    public PostResponse replacePost(@Valid @RequestBody PostRequest postRequest,
                                    @PathVariable long id) {
        Post post = postService.replacePost(id, postRequest);
        return PostMapper.INSTANCE.toResponse(post);
    }

    @PatchMapping("/{id}")
    public PostResponse updatePost(@RequestBody PostPatchRequest postRequest,
                                    @PathVariable long id) {
        Post post = postService.updatePost(id, postRequest);
        return PostMapper.INSTANCE.toResponse(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable long id){
        postService.deletePost(id);
    }

}
