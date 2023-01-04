package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.*;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class User implements UserDetails {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    @GraphQLQuery(name = "id",description = "Идентификатор на корисник")
    private int userId;
    @Basic
    @Column(name = "username")
    @GraphQLNonNull
    @GraphQLQuery(name = "username",description = "Корисничко име")
    private String username;
    @Basic
    @Column(name = "name")
    @GraphQLQuery(name = "name",description = "Име")
    @GraphQLNonNull
    private String name;
    @Basic
    @Column(name = "surname")
    @GraphQLQuery(name = "surname",description = "Презиме")
    @GraphQLNonNull
    private String surname;
    @Basic
    @Column(name = "email")
    @GraphQLNonNull
    @GraphQLQuery(name = "email",description = "Емаил")
    private String email;
    @Basic
    @Column(name = "password")
    @GraphQLNonNull
    private String password;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "discussions",description = "Дискусии")
    private Collection<Discussion> discussions;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "movieLikes",description = "Допаднати филмови")
    private Collection<MovieLikes> movieLikes;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "userMovieRates",description = "Оценети филмови")
    private Collection<MovieRates> movieRates;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "userPersonRates",description = "Оценети личности")
    private Collection<PersonRates> personRates;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "replies",description = "Реплики")
    private Collection<Reply> replies;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "userGenres",description = "Допаднати жанрови")
    private Collection<UserGenres> userGenres;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "userReplies",description = "Допаднати реплики")
    private Collection<UserReplies> userReplies;

    @OneToMany(mappedBy = "user")
    @GraphQLQuery(name = "discussionLikes",description = "Допаднати дискусии")
    private Collection<DiscussionLikes> discussionLikes;

    public User() {

    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    public User( String email,String username , String password, String name,String surname)  {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
