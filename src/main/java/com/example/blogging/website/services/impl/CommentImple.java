package com.example.blogging.website.services.impl;

import com.example.blogging.website.entity.Comment;
import com.example.blogging.website.entity.Post;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.payload.CommentDto;
import com.example.blogging.website.repository.CommentRepo;
import com.example.blogging.website.repository.PostRepo;
import com.example.blogging.website.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentImple implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id ",postId));
       Comment comment =  modelMapper.map(commentDto, Comment.class);
       comment.setPost(post);
       Comment savedCom = commentRepo.save(comment);
        return this.modelMapper.map(savedCom,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id ",commentId));
        commentRepo.deleteById(commentId);
    }
}
