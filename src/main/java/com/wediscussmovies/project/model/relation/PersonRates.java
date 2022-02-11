package com.wediscussmovies.project.model.relation;

import com.wediscussmovies.project.model.Person;
import com.wediscussmovies.project.model.User;
import com.wediscussmovies.project.model.primarykeys.PersonRatesPK;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person_rates", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class PersonRates {

    @EmbeddedId
    private PersonRatesPK id;

    @Basic
    @Column(name = "reason")
    @GraphQLNonNull
    @GraphQLQuery(name = "reason",description = "Причина")
    private String reason;
    @Basic
    @Column(name = "stars_rated")
    @GraphQLNonNull
    @GraphQLQuery(name = "stars",description = "Оцена")
    private int starsRated;


    @ManyToOne
    @JoinColumn(name = "person_id")
    @MapsId("person_id")
    @GraphQLNonNull
    @GraphQLQuery(name = "person",description = "Оцена")
    private Person person;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("user_id")
    @GraphQLNonNull
    @GraphQLQuery(name = "userForum",description = "Оцена")
    private User user;


    public PersonRates() {
    }

    public PersonRates(User user, Person person, String reason, Integer starsRated) {
        this.id = new PersonRatesPK(person.getPersonId(), user.getUserId());
        this.reason = reason;
        this.starsRated = starsRated;
        this.person = person;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRates that = (PersonRates) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
