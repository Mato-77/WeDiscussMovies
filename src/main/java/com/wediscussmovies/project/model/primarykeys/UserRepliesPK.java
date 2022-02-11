package com.wediscussmovies.project.model.primarykeys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserRepliesPK implements Serializable {

    @Column(name = "discussion_id")
    private Integer discussionId;
    @Column(name = "reply_id")
    private Integer replyId;
    @Column(name = "user_id")
    private Integer userId;


}
