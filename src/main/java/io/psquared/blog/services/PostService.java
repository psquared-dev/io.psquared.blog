package io.psquared.blog.services;

import io.psquared.blog.dto.PostPatchRequest;
import io.psquared.blog.dto.PostRequest;
import io.psquared.blog.dto.PostResponse;
import io.psquared.blog.entity.Category;
import io.psquared.blog.entity.Post;
import io.psquared.blog.exceptions.NotFound;
import io.psquared.blog.repository.CategoryRepository;
import io.psquared.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository,  CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public Post ceatePost(PostRequest postRequest) {
        Post newPost = new Post();

        Category category = categoryRepository
                .findByName(postRequest.getCategory())
                .orElseThrow(() ->
                        new NotFound("No category found with name: " + postRequest.getCategory())
                );

        newPost.setTitle(postRequest.getTitle());
        newPost.setContent(postRequest.getContent());
        newPost.setCategory(category);
        return postRepository.save(newPost);
    }

    public Optional<Post> getPost(long id) {
        return postRepository.findById(id);
    }

    public Post replacePost(long id, PostRequest postRequest) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new NotFound("Not found post with id: " + id));

        Category category = categoryRepository
                .findByName(postRequest.getCategory())
                .orElseThrow(
                        () -> new NotFound("Not found category with name: " + postRequest.getCategory())
                );

        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setCategory(category);

        return postRepository.save(post);
    }

    public Post updatePost(long id, PostPatchRequest postRequest) {
        Post post = postRepository
                .findById(id)
                .orElseThrow(() -> new NotFound("Not found post with id: " + id));

        if(postRequest.getTitle() != null ){
            post.setTitle(postRequest.getTitle());
        }

        if(postRequest.getContent() != null ){
            post.setContent(postRequest.getContent());
        }

        if(postRequest.getCategory() != null ){
            Category category = categoryRepository
                    .findByName(postRequest.getCategory())
                    .orElseThrow(
                            () -> new NotFound("Not found category with name: " + postRequest.getCategory())
                    );
            post.setCategory(category);
        }

        return postRepository.save(post);
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
