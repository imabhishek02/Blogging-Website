package com.example.blogging.website.controllers;

import com.example.blogging.website.config.AppConstants;
import com.example.blogging.website.entity.Category;
import com.example.blogging.website.entity.Post;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.payload.*;
import com.example.blogging.website.repository.PostRepo;
import com.example.blogging.website.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepo postRepo;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId) {
        PostDto newPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy
    ) {
        PostResponse postResponse = postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.FOUND);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>>getpostwithcategory(@PathVariable Integer categoryId){
        List<PostDto>postDtos = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.FOUND );
    }
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<List<PostDto>>getPostbyUserId(@PathVariable Integer userId){
        List<PostDto>postDtos = this.postService.getPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.FOUND);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto postDto1 = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse>deletePost(@PathVariable Integer postId){
        if(postRepo.existsById(postId)){
            this.postService.deletePost(postId);
            return  new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted",true),HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Not Found","",404);
        }
    }
    @GetMapping("/search/{keyword}")
     public ResponseEntity<List<PostDto>>searchByTitle(@PathVariable String keyword){
        List<PostDto>searchByTitle = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(searchByTitle,HttpStatus.OK);
     }
}
