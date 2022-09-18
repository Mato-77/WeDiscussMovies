package com.wediscussmovies.project.model.primarykeys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@Data
public class PersonRatesPK implements Serializable {

    @Column(name = "person_id")
    private int personId;

    @Column(name = "user_id")
    private int userId;

    public PersonRatesPK() {
    }

    public PersonRatesPK(int personId, int userId) {
        this.personId = personId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonRatesPK that = (PersonRatesPK) o;


        return personId == that.personId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + userId;
        return result;
    }
}
