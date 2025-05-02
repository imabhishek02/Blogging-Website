package com.example.blogging.website.payload;

import com.example.blogging.website.entity.Category;
import com.example.blogging.website.entity.Comment;
import com.example.blogging.website.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Data
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto>comments;
}
