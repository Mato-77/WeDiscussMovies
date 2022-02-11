package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "replies", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
@IdClass(ReplyPK.class)
public class Reply implements Serializable {

    @Column(name = "discussion_id")
    @Id
    @GraphQLQuery(name = "discussionId",description = "Идентификатор на реплика")
    private int discussionId;

    @Column(name = "reply_id")
    @Id
    @GraphQLQuery(name = "id",description = "Идентификатор на реплика")
    private int replyId;

    @Basic
    @Column(name = "text")
    @GraphQLNonNull()
    @GraphQLQuery(name = "text",description = "Текст")
    private String text;

    @Basic
    @Column(name = "date")
    @GraphQLNonNull()
    @GraphQLQuery(name = "date",description = "Датум на објава")
    private LocalDate date;



    @ManyToOne
    @MapsId("discussion_id")
    @JoinColumn(name = "discussion_id")
    @GraphQLQuery(name = "discussion",description = "Дискусија")
    private Discussion discussion;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user_id")
    @GraphQLNonNull
    @GraphQLQuery(name = "userForum",description = "Креатор на дискусија")
    private User user;

    public Reply() {
    }

    public Reply(Discussion discussion, String text, LocalDate date, User user) {
        this.discussion = discussion;
        this.discussionId = discussion.getDiscussionId();
        this.text = text;
        this.date = date;
        this.user = user;
    }


}
