package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.DiscussionLikes;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
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
    private Date date;





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
    private Long likes;

    public Discussion(Character type, String text, String title, Date date, User user) {
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
