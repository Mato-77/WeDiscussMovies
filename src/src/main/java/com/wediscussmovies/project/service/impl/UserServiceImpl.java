package com.wediscussmovies.project.service.impl;

import com.wediscussmovies.project.model.exception.InvalidArgumentsException;
import com.wediscussmovies.project.model.exception.PasswordsDoNotMatchException;
import com.wediscussmovies.project.model.exception.UserNotExistException;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.exception.UsernameAlreadyExistsException;
import com.wediscussmovies.project.model.relation.UserReplies;
import com.wediscussmovies.project.repository.UserRepliesRepository;
import com.wediscussmovies.project.repository.UserRepository;
import com.wediscussmovies.project.service.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepliesRepository userRepliesRepository;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRepliesRepository userRepliesRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepliesRepository = userRepliesRepository;
    }

    @Override
    @GraphQLQuery(name = "userByUsername")
    public User findByUsername(@GraphQLArgument(name = "username") String username) {
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
        return this.userRepository.save(user);

    }

    @Override
    @GraphQLQuery(name = "user")
    public User findById(@GraphQLArgument(name = "id") Integer id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotExistException(id.toString()));
    }

    @Override
    @GraphQLQuery(name = "users")
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    @GraphQLQuery(name = "usersReplies")
    public List<UserReplies> findAllUserReplies() {
        return this.userRepliesRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUsername(username);
    }
}
