package com.wediscussmovies.project.ajaxmodels;

import lombok.Data;

@Data
public class Grade {
    private Integer rating;
    private String reason;

    public Grade(String rating, String reason) {
        this.rating = Integer.parseInt(rating);
        this.reason = reason;
    }
}
