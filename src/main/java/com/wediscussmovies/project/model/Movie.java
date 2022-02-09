package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.MovieLikes;
import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.MovieGenres;
import com.wediscussmovies.project.model.relation.MovieRates;
import lombok.Data;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.sql.Date;
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
    private int movieId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    @Column(name = "airing_date")
    private Date airingDate;
    @Basic
    @Column(name = "imdb_rating")
    private Double imdbRating;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private Collection<MovieActors> actors;
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private Collection<MovieGenres> genres;
    @OneToMany(mappedBy = "movie")
    private Collection<MovieLikes> likes;
    @OneToMany(mappedBy = "movie")
    private Collection<MovieRates> rates;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Person director;

    public Movie() {

    }

    public Movie(String title, String description, String imageUrl, Date airingDate,
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
