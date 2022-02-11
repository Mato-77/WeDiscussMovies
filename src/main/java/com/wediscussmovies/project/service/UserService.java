package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.User;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
     User findByUsername( String username);

     User register(String email, String username, String password, String confirmPassword, String name, String surname);


    User findById(Integer id);

     List<User> findAll();
}
