package com.example.blogging.website.services;

import com.example.blogging.website.entity.User;
import com.example.blogging.website.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto dtoUser,Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUser();
    void deleteUser(Integer userId);

}
