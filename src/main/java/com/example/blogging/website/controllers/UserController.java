package com.example.blogging.website.controllers;

import com.example.blogging.website.entity.User;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.payload.ApiResponse;
import com.example.blogging.website.payload.UserDto;
import com.example.blogging.website.repository.UserRepo;
import com.example.blogging.website.services.JwtService;
import com.example.blogging.website.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto obj  = this.userService.createUser(userDto);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody UserDto userDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(),userDto.getPassword()));

        if(authentication.isAuthenticated()){
           return  jwtService.generateToken(userDto.getEmail());
        }else{
            return "failure";
        }
    }


    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable int id)
    {
        if(userRepo.existsById(id)){
            UserDto updateUser = this.userService.updateUser(userDto,id);
            return new ResponseEntity<>(updateUser,HttpStatus.ACCEPTED);
        }else{
            throw new ResourceNotFoundException("Not Found","",404);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id){
        if(userRepo.existsById(id)){
            UserDto getUser = this.userService.getUserById(id);
            return new ResponseEntity<>(getUser,HttpStatus.FOUND);
        }else{
            throw new ResourceNotFoundException("","",id);
        }
    }
   // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> allUser = this.userService.getAllUser();
        return new ResponseEntity<>(allUser,HttpStatus.FOUND);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUse(@PathVariable int id){
        if(userRepo.existsById(id)){
            this.userService.deleteUser(id);
            return  new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted",true),HttpStatus.OK);
        }else {
            throw new ResourceNotFoundException("Not Found","",404);
        }
    }
}
