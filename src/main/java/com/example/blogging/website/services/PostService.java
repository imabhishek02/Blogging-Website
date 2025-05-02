package com.example.blogging.website.services;

import com.example.blogging.website.entity.Post;
import com.example.blogging.website.payload.PostDto;
import com.example.blogging.website.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);

    PostDto getPostById(Integer postId);

    List<PostDto>getPostByCategory(Integer categoryId);

    List<PostDto>getPostByUser(Integer userId);

    List<PostDto>searchPosts(String keyword);
}
