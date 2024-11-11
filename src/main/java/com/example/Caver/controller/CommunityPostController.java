package com.example.Caver.controller;

import com.example.Caver.model.CommunityPost;
import com.example.Caver.repository.CommunityPostRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommunityPostController {

    private final CommunityPostRepo postRepository;

    public CommunityPostController(CommunityPostRepo postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<CommunityPost> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping
    public CommunityPost createPost(@RequestBody CommunityPost post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public CommunityPost updatePost(@PathVariable Long id, @RequestBody CommunityPost updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
}
