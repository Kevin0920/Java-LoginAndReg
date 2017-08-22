package com.example.auth.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.auth.models.User;
import com.example.auth.repositories.UserRepository;

@Service
public class UserDetailsServiceImplementaion implements UserDetailsService {
    private UserRepository userRepository;
    
    public UserDetailsServiceImplementaion(UserRepository userRepository){
    this.userRepository = userRepository;
    }
    
    // 1
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }
    
    // 2
    private List<GrantedAuthority> getAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
       
        return authorities;
    }
}
