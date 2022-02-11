package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.MovieGenres;
import com.wediscussmovies.project.model.relation.MovieRates;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "movies", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "movie_id")
    @GraphQLQuery(name = "id",description = "Идентификатор")
    private int movieId;
    @Basic
    @Column(name = "title")
    @GraphQLNonNull()
    @GraphQLQuery(name = "title",description = "Наслов")
    private String title;
    @Basic
    @Column(name = "description")
    @GraphQLNonNull()
    @GraphQLQuery(name = "description",description = "Опис")
    private String description;
    @Basic
    @Column(name = "image_url")
    @GraphQLNonNull()
    @GraphQLQuery(name = "image",description = "Линк кон слика")
    private String imageUrl;
    @Basic
    @Column(name = "airing_date")
    @GraphQLNonNull()
    @GraphQLQuery(name = "date",description = "Датум на издавање")
    private LocalDate airingDate;
    @Basic
    @Column(name = "imdb_rating")
    @GraphQLQuery(name = "imbdRating",description = "Рејтинг според останати организации")
    private Double imdbRating;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @GraphQLQuery(name = "actors",description = "Актери")
    private Collection<MovieActors> actors;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @GraphQLQuery(name = "genres",description = "Актери")
    private Collection<MovieGenres> genres;
    @OneToMany(mappedBy = "movie")
    @GraphQLQuery(name = "likes",description = "Лајкови")
    private Collection<MovieLikes> likes;
    @OneToMany(mappedBy = "movie")
    @GraphQLQuery(name = "rates",description = "Лајкови")
    private Collection<MovieRates> rates;

    @OneToMany(mappedBy = "movie")
    @GraphQLQuery(name = "discussions",description = "Дискусии")
    private Collection<Discussion> discussions;
    @ManyToOne
    @JoinColumn(name = "director_id")
    @GraphQLQuery(name = "director",description = "Режисер")
    private Person director;

    public Movie() {

    }

    public Movie(String title, String description, String imageUrl, LocalDate airingDate,
                 Double imdbRating, Person director) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.airingDate = airingDate;
        this.imdbRating = imdbRating;
        this.director = director;

    }

    public Movie(int movieId, String title, Double imdbRating, String imageUrl) {
        this.movieId = movieId;
        this.title = title;
        this.imdbRating = imdbRating;
        this.imageUrl = imageUrl;
    }

    public String getDateFormatted(){
        String dob = airingDate.toString();
        String [] parts = dob.split("-");
        return parts[2]+"/"+parts[1]+"/"+parts[0];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieId == movie.movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }

    public static Comparator<Movie> comparatorTitle = Comparator.comparing(Movie::getTitle);


    public String getShortTitle(){
        int to = 20;
        if (title.length() < to)
            to = title.length();
        if(to<20)
            return title;
        return title.substring(0, to) + "...";
    }




}
