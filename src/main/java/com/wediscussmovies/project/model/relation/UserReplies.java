package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.UserRepliesPK;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "reply_likes",schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
@NoArgsConstructor
public class UserReplies {

    @EmbeddedId
    private UserRepliesPK id;



    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    @GraphQLQuery(name = "user",description = "Корисник")
    private User user;

    public UserReplies(Integer discussionId, Integer replyId, Integer userId){
        this.id = new UserRepliesPK(discussionId,replyId,userId);
    }


}
