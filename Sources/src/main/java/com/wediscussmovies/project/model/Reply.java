package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "replies", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
@IdClass(ReplyPK.class)
public class Reply implements Serializable {

    @Column(name = "discussion_id")
    @Id
    private int discussionId;

    @Column(name = "reply_id")
    @Id
    private int replyId;

    @Basic
    @Column(name = "text")
    private String text;

    @Basic
    @Column(name = "date")
    private Date date;



    @ManyToOne
    @MapsId("discussion_id")
    @JoinColumn(name = "discussion_id")
    private Discussion discussion;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Reply() {
    }

    public Reply(Discussion discussion, String text, Date date, User user) {
        this.discussion = discussion;
        this.discussionId = discussion.getDiscussionId();
        this.text = text;
        this.date = date;
        this.user = user;
    }


}
