/*
 * funkcija koja ke bide check_constraint za tip na person vo soodvetnite relacii
 */
create function check_constraint_person(integer) returns char
		as $$ select type from persons where person_id = $1 $$
		language sql;


-- dodavanje tabeli
create table users(
  user_id serial primary key,
  username varchar(50) not null unique,
  name varchar(50) not null,
  surname varchar(50) not null,
  email varchar(100) not null unique,
  password varchar(30) not null,
  constraint ck_password check(length(password) >= 9)
);

create table persons(
    person_id serial primary key,
    name varchar(100) not null,
    surname varchar(100) not null,
    type char(1) not null,
    date_of_birth date not null,
    image_url varchar(300) not null,
    description varchar(300) not null,
    constraint ck_type check (type ='A' or type='D')
);

create table movies(
    movie_id serial primary key,
    title varchar(150) not null unique,
    description varchar(1000) not null,
    image_url varchar(300) not null,
    airing_date date not null,
    imdb_rating float,
    director_id integer,
    constraint fk_movie_director foreign key (director_id) references persons(person_id)
    on delete cascade on update cascade,
    constraint ck_person_is_director check( check_constraint_person(director_id) = 'D')
    /*
     * dodaden uslov za foreing key
     */
);

create table genres(
    genre_id serial primary key,
    genre_type varchar(100) not null unique
);

create table movie_rates(
    movie_id integer,
    user_id integer,
    reason varchar(300) not null,
    stars_rated integer not null,
    constraint pk_movie_rates primary key (movie_id,user_id),
    constraint fk_rated_movie foreign key (movie_id) references movies(movie_id)
    on delete cascade on update cascade,
    constraint fk_user_rate foreign key (user_id) references users(user_id)
    on delete cascade on update cascade,
    constraint ck_movie_stars check (stars_rated >= 1 and stars_rated <= 10)
);

create table movie_likes(
    movie_id integer,
    user_id integer,
    constraint pk_movie_likes primary key (movie_id,user_id),
    constraint fk_liked_movie foreign key (movie_id) references movies(movie_id)
    on delete cascade on update cascade,
    constraint fk_user_like foreign key (user_id) references users(user_id)
    on delete cascade on update cascade    
);

create table movie_genres(
    movie_id integer,
    genre_id integer,
    constraint pk_movie_genres primary key (movie_id,genre_id),
    constraint fk_movie_genre foreign key (movie_id) references movies(movie_id)
    on delete cascade on update cascade,
    constraint fk_genre_type_movie foreign key (genre_id) references genres(genre_id)
    on delete cascade on update cascade
);

create table user_genres(
    user_id integer,
    genre_id integer,
    constraint pk_user_genres primary key (user_id,genre_id),
    constraint fk_user_genre foreign key (user_id) references users(user_id)
    on delete cascade on update cascade,
    constraint fk_genre_like_user foreign key (genre_id) references genres(genre_id)
    on delete cascade on update cascade    
);

create table movie_actors(
    movie_id integer,
    actor_id integer,
    constraint pk_movie_actors primary key (movie_id,actor_id),
    constraint fk_actors_in_movie foreign key (movie_id) references movies(movie_id)
    on delete cascade on update cascade,
    constraint fk_actor_acts_movie foreign key (actor_id) references persons(person_id)
    on delete cascade on update cascade,
    constraint ck_person_is_actor check(check_constraint_person(actor_id) = 'A')
/*
 * dodaeno
 * potrebno e i ovde ogranicuvanje na actor_id, negovoto svojstvo type = 'A'
 */    
);

create table person_rates(
    person_id integer,
    user_id integer,
    reason varchar(300) not null,
    stars_rated integer not null,
    constraint pk_person_rates primary key (person_id,user_id),
    constraint fk_rated_person foreign key (person_id) references persons(person_id)
    on delete cascade on update cascade,
    constraint fk_user_rate_person foreign key (user_id) references users(user_id)
    on delete cascade on update cascade,
    constraint ck_person_stars check (stars_rated >= 1 and stars_rated <= 10)
);

create table discussions(
	discussion_id serial primary key,
	type char(1) not null,
    text varchar(1000) not null,
	title varchar(250) not null,
	date date not null,
	user_id integer not null,
	movie_id integer,
	person_id integer,
	constraint fk_user_created foreign key (user_id) references users(user_id)
	on delete cascade on update cascade,
	constraint ck_type_discussion check( (type = 'M' and movie_id notnull and person_id isnull)
	or (type='P' and person_id  notnull and movie_id isnull)),
	constraint fk_discussion_movie foreign key (movie_id) references movies(movie_id)
	on delete cascade on update cascade,
	constraint fk_discussion_person foreign key (person_id) references persons(person_id)
	on delete cascade on update cascade
);



create table replies(
	discussion_id integer,
	reply_id serial,
	text varchar(1000) not null,
	date date not null,
	user_id integer not null,
	constraint pk_replies primary key(discussion_id,reply_id),
	constraint fk_user_create_reply foreign key (user_id) references users(user_id)
	on delete cascade on update cascade,
	constraint fk_reply_discussion foreign key (discussion_id) references discussions(discussion_id)
    on delete cascade on update cascade

	
);


create table reply_likes(
	reply_id integer,
	discussion_id integer,
	user_id integer,
    constraint pk_reply_likes primary key (reply_id, discussion_id,user_id),
	constraint fk_user_like foreign key (user_id) references users(user_id)
    on delete cascade on update cascade,
    constraint fk_reply_like foreign key (reply_id, discussion_id) references replies(reply_id, discussion_id)
    on delete cascade on update cascade
);



create table discussion_likes(
    discussion_id integer,
    user_id integer,
    constraint pk_discussion_likes primary key (discussion_id,user_id),
    constraint fk_liked_discussion foreign key (discussion_id) references discussions(discussion_id)
    on delete cascade on update cascade,
    constraint fk_user_like foreign key (user_id) references users(user_id)
    on delete cascade on update cascade 
);




-- brisenje tabeli
drop table if exists reply_likes;
drop table if exists discussion_likes;
drop table if exists replies;
drop table if exists discussions;
drop table if exists person_rates;
drop table if exists movie_actors;
drop table if exists user_genres;
drop table if exists movie_genres;
drop table if exists movie_likes;
drop table if exists movie_rates;
drop table if exists genres;
drop table if exists movies;
drop table if exists persons;
drop table if exists users;










