package com.api.security.service;

import com.api.security.entities.User;
import com.api.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user =userRepository.findByEmail(email);

      if(user==null){
          throw new UsernameNotFoundException("Bad user");
      }
      return new CustomUserDetails(user);
    }
}
