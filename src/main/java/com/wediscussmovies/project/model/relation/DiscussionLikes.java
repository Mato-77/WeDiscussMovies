package com.wediscussmovies.project.model.relation;


import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Movie;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.DiscussionLikesPK;
import com.wediscussmovies.project.model.primarykeys.MovieLikesPK;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "discussion_likes", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class DiscussionLikes {
    @EmbeddedId
    private DiscussionLikesPK id;

    @ManyToOne
    @MapsId("discussion_id")
    @JoinColumn(name = "discussion_id")
    @GraphQLQuery(name = "discussion",description = "Дискусија")
    private Discussion discussion;


    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    @GraphQLQuery(name = "userForum",description = "Корисник")
    private User user;

    public DiscussionLikes(Discussion discussion, User user) {
        this.id = new DiscussionLikesPK(discussion.getDiscussionId(), user.getUserId());
        this.discussion = discussion;
        this.user = user;
    }

    public DiscussionLikes() {
    }
}
