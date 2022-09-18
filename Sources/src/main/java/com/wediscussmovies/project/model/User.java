package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.UserGenres;
import com.wediscussmovies.project.model.relation.MovieRates;
import com.wediscussmovies.project.model.relation.PersonRates;
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
    private int userId;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    private Collection<Discussion> discussions;
    @OneToMany(mappedBy = "user")
    private Collection<MovieLikes> movieLikes;
    @OneToMany(mappedBy = "user")
    private Collection<MovieRates> movieRates;
    @OneToMany(mappedBy = "user")
    private Collection<PersonRates> personRates;
    @OneToMany(mappedBy = "user")
    private Collection<Reply> replies;
    @OneToMany(mappedBy = "user")
    private Collection<UserGenres> userGenres;

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
