package com.example.blogging.website.security;

import com.example.blogging.website.entity.User;
import com.example.blogging.website.exception.ResourceNotFoundException;
import com.example.blogging.website.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading user from database by username
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User ","UserName"+username,0));

        return user;
    }
}
