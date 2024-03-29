package com.wediscussmovies.project.model;

import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "discussions", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class Discussion {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "discussion_id")
    @GraphQLQuery(name = "id",description = "Идентификатор")
    private int discussionId;
    @Basic
    @Column(name = "type")
    @GraphQLNonNull
    @GraphQLQuery(name = "type",description = "Тип")
    private Character type;
    @Basic
    @Column(name = "text")
    @GraphQLNonNull
    @GraphQLQuery(name = "text",description = "Текст")
    private String text;
    @Basic
    @Column(name = "title")
    @GraphQLNonNull
    @GraphQLQuery(name = "title",description = "Наслов")
    private String title;
    @Basic
    @Column(name = "date")
    @GraphQLNonNull
    @GraphQLQuery(name = "date",description = "Датум на креирање")
    private LocalDate date;





    @ManyToOne
    @JoinColumn(name = "movie_id")
    @GraphQLQuery(name = "movie",description = "Филм")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @GraphQLNonNull
    @GraphQLQuery(name = "user",description = "Корисник")
    private User user;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @GraphQLQuery(name = "person",description = "Личност")
    private Person person;

    @OneToMany(mappedBy = "discussion")
    @GraphQLQuery(name = "replies",description = "Реплики")
    private Collection<Reply> replies;

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
