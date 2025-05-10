package com.example.blogging.website.repository;

import com.example.blogging.website.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}