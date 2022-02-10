package com.wediscussmovies.project.service;

import com.wediscussmovies.project.model.User;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    @GraphQLQuery(name = "userByUsername")
     User findByUsername(@GraphQLArgument(name = "username") String username);

     User register(String email, String username, String password, String confirmPassword, String name, String surname);

     @GraphQLQuery(name = "user")
    User findById(@GraphQLArgument(name = "id") Integer id);
}
