package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.exception.InvalidArgumentsException;
import com.wediscussmovies.project.model.exception.PasswordsDoNotMatchException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.exception.UsernameAlreadyExistsException;
import com.wediscussmovies.project.repository.UserRepository;
import com.wediscussmovies.project.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotExistException(username));
    }



    @Override
    public User register( String email, String username, String password, String confirmPassword
                                  , String name, String surname) {

        if ( username.isEmpty() || password.isEmpty())
            throw new InvalidArgumentsException();
        if (!password.equals(confirmPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(email,username,passwordEncoder.encode(password),name,surname);
        return userRepository.save(user);

    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotExistException(id.toString()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
