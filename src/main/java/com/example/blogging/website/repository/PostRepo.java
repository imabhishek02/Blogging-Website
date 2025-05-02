package com.example.blogging.website.repository;

import com.example.blogging.website.entity.Category;
import com.example.blogging.website.entity.Post;
import com.example.blogging.website.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
