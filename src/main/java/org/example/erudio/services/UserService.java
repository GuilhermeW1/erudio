package org.example.erudio.services;


import org.example.erudio.data.dto.mapper.custom.PersonMapper;

import org.example.erudio.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Find one user by name "+ username + "!");
        var user = userRepository.findUserByUserName(username);

        if (user == null) throw new UsernameNotFoundException("Username "+ username + "not found!");

        return user;
    }
}
