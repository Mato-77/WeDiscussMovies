package com.wediscussmovies.project.model;

import com.wediscussmovies.project.model.relation.MovieActors;
import com.wediscussmovies.project.model.relation.PersonRates;
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
    private int personId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "surname")
    private String surname;
    @Basic
    @Column(name = "type")
    private Character type;
    @Basic
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Basic
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "person")
    private Collection<Discussion> discussions;
    @OneToMany(mappedBy = "person")
    private Collection<MovieActors> movieActors;
    @OneToMany(mappedBy = "director")
    private Collection<Movie> movies;
    @OneToMany(mappedBy = "person")
    private Collection<PersonRates> personRates;



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


    public Person(String name, String surname, Character type, Date dateOfBirth, String imageUrl, String description) {
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public boolean hasGradeFromUser(User user){
        for(PersonRates p: personRates){
            if(p.getUser().getUserId() == user.getUserId())
                return true;
        }
        return false;
    }

}
