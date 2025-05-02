package com.example.blogging.website.services.impl;

import com.example.blogging.website.entity.Category;
import com.example.blogging.website.entity.Post;
import com.example.blogging.website.entity.User;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.payload.PostDto;
import com.example.blogging.website.payload.PostResponse;
import com.example.blogging.website.repository.CategoryRepo;
import com.example.blogging.website.repository.PostRepo;
import com.example.blogging.website.repository.UserRepo;
import com.example.blogging.website.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("userId","Id",userId)  );
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("categoryId","Id",categoryId)  );
        Post post = postDtoTopost(postDto);
        post.setImageName("Default.png");
        LocalDate dt = LocalDate.now();
        post.setAddedDate(dt);
        post.setUser(user);
        post.setCategory(category);
        Post newPost= postRepo.save(post);
        return postToPostdto(newPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("","",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        postRepo.save(post);
        PostDto updatedPost = postToPostdto(post);
        return updatedPost;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("","",postId));
        postRepo.deleteById(postId);
        return;
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize,Sort.by(sortBy));
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post>posts = pagePost.getContent();
        if(posts.isEmpty()){
            throw new ResourceNotFoundException("Posts "," Posts ",0);
        }
        List<PostDto>postDtos = posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class) ).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pageNumber);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("PostId"," PostId ",postId));
        PostDto postDto = postToPostdto(post);
        return postDto;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("categoryId","categoryId",categoryId));
        List<Post> allPost = this.postRepo.findByCategory(cat);
        List<PostDto>  postDtos=   allPost.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("userId"," userId ",userId));
        List<Post>posts = this.postRepo.findByUser(user);
        List<PostDto>postDtos = posts.stream().map((post) ->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
         List<Post> posts = this.postRepo.findByTitleContaining(keyword);
         List<PostDto>postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    public PostDto postToPostdto(Post post){
        return modelMapper.map(post,PostDto.class);
    }

    public Post postDtoTopost(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }

}
