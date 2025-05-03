package com.example.blogging.website.services.impl;

import com.example.blogging.website.entity.User;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.payload.UserDto;
import com.example.blogging.website.repository.UserRepo;
import com.example.blogging.website.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        User obj = userDtoToUser(userDto);
        BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder(12);
        String ps = encrypt.encode(userDto.getPassword());
        obj.setPassword(ps);
        this.userRepo.save(obj);
        return userDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        User updateUser = this.userRepo.save(user);
        UserDto updatedUserDto = this.userToUserDto(updateUser);
        return updatedUserDto;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User"," Id ",userId));
        this.userRepo.deleteById(userId);

    }
    private User userDtoToUser(UserDto userDto){
        User obj = this.modelMapper.map(userDto,User.class);
//        obj.setId(userDto.getId());
//        obj.setName(userDto.getName());
//        obj.setEmail(userDto.getEmail());
//        obj.setPassword(userDto.getPassword());
//        obj.setAbout(userDto.getAbout());
        return  obj;
    }
    private UserDto userToUserDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
