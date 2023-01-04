package com.wediscussmovies.project.repository;

import com.wediscussmovies.project.model.Discussion;
import com.wediscussmovies.project.model.Reply;
import com.wediscussmovies.project.model.primarykeys.ReplyPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface ReplyRepository extends JpaRepository<Reply, ReplyPK> {
    List<Reply> findAllByDiscussion(Discussion discussion);

    @Modifying
    @Query(value = "insert into project.replies (text,date,user_id,discussion_id) values(:text,:date,:user_id,:discussion_id)",nativeQuery = true)
    @Transactional
    void insertInto(@Param("text") String text, @Param("date") LocalDate date,
                    @Param("user_id")Integer userId,@Param("discussion_id")Integer discussionId);
}
