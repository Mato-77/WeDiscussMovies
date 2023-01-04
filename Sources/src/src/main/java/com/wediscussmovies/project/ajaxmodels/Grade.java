package com.wediscussmovies.project.ajaxmodels;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

@Data
public class Grade {
    @GraphQLQuery(name = "rating",description = "Рејтинг")
    private Integer rating;
    @GraphQLQuery(name = "reason",description = "Причина")
    private String reason;

    public Grade(String rating, String reason) {
        this.rating = Integer.parseInt(rating);
        this.reason = reason;
    }
}
