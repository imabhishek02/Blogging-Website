package com.example.blogging.website.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
