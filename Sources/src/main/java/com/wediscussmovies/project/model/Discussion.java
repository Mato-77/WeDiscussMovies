package com.wediscussmovies.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "discussions", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class Discussion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "discussion_id")
    private int discussionId;
    @Basic
    @Column(name = "type")
    private Character type;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "date")
    private LocalDate date;





    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Transient
    private long likes;

    public Discussion(Character type, String text, String title, LocalDate date, User user) {
        this.type = type;
        this.text = text;
        this.title = title;
        this.date = date;
        this.user = user;
    }



    public Discussion() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discussion that = (Discussion) o;
        return discussionId == that.discussionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(discussionId);
    }
}
