package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.PersonRates;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.Data;

import javax.persistence.*;
import javax.swing.text.DateFormatter;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Entity
@Table(name = "persons", schema = "project", catalog = "db_202122z_va_prj_wediscussmovies")
@Data
public class Person {

    /*
    ni fale tabela za person koj zanrovi gi ima - ako e potrebno
     */

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "person_id")
    @GraphQLQuery(name = "id",description = "Идентификатор")
    private int personId;
    @Basic
    @Column(name = "name")
    @GraphQLNonNull()
    @GraphQLQuery(name = "name",description = "Име")
    private String name;
    @Basic
    @Column(name = "surname")
    @GraphQLNonNull()
    @GraphQLQuery(name = "surname",description = "Презиме")
    private String surname;
    @Basic
    @Column(name = "type")
    @GraphQLNonNull()
    @GraphQLQuery(name = "type",description = "Тип")
    private Character type;
    @Basic
    @Column(name = "date_of_birth")
    @GraphQLNonNull()
    @GraphQLQuery(name = "date",description = "Датум раѓање")
    private LocalDate dateOfBirth;
    @Basic
    @Column(name = "image_url")
    @GraphQLNonNull()
    @GraphQLQuery(name = "image",description = "Слика")
    private String imageUrl;
    @Basic
    @Column(name = "description")
    @GraphQLNonNull()
    @GraphQLQuery(name = "description",description = "Опис")
    private String description;
    @OneToMany(mappedBy = "person")
    @GraphQLQuery(name = "discussions",description = "Оцена")
    private Collection<Discussion> discussions;
    @OneToMany(mappedBy = "person")
    @GraphQLQuery(name = "actors",description = "Оцена")
    private Collection<MovieActors> actors;
    @OneToMany(mappedBy = "director")
    @GraphQLQuery(name = "movies",description = "Оцена")
    private Collection<Movie> movies;
    @OneToMany(mappedBy = "person")
    @GraphQLQuery(name = "rates",description = "Оцена")
    private Collection<PersonRates> rates;



    public Person() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person that = (Person) o;

        return personId == that.getPersonId();
    }

    @Override
    public int hashCode() {

        return personId * 31;
    }


    public String getDateFormatted(){
        String dob = dateOfBirth.toString();
        String [] parts = dob.split("-");
        return parts[2]+"/"+parts[1]+"/"+parts[0];
    }


    public Person(String name, String surname, Character type, LocalDate dateOfBirth, String imageUrl, String description) {
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
        this.description = description;
    }

//    public boolean hasGradeFromUser(User user){
//        for(PersonRates p: personRates){
//            if(p.getUser().getUserId() == user.getUserId())
//                return true;
//        }
//        return false;
//    }

}
