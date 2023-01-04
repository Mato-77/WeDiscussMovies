insert into persons (name, surname, type, date_of_birth, image_url, description) VALUES
	('Quentin', 'Tarantino', 'D', to_date('27-04-1963','DD-MM-YYYY'),'https://upload.wikimedia.org/wikipedia/commons/0/0b/Quentin_Tarantino_by_Gage_Skidmore.jpg', 'Quentin Jerome Tarantino is an American film director, screenwriter, producer, film critic, and actor. His films are characterized by nonlinear storylines, dark humor, stylized violence, extended dialogue, ensemble casts, references to popular culture, alternate history, and neo-noir.' ),
	('Steven', 'Spielberg', 'D', to_date('18-12-1946','DD-MM-YYYY'),'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHVS2ee6usybjD-SvuXSvnWU2E3qf7-5g5YOTbUK71OHqtPVTY','Steven Allan Spielberg is an American film director, producer, and screenwriter. He began his career in the New Hollywood era and is currently the most commercially successful director.'),
	('Christopher', 'Nolan', 'D', to_date('30-07-1970','DD-MM-YYYY'),'https://upload.wikimedia.org/wikipedia/commons/9/95/Christopher_Nolan_Cannes_2018.jpg','Christopher Edward Nolan CBE is a British-American film director, producer, and screenwriter. His films have grossed more than US$5 billion worldwide, and have garnered 11 Academy Awards from 36 nominations. Born and raised in London, Nolan developed an interest in filmmaking from a young age.'),
	('Tom', 'Hanks', 'A', to_date('09-07-1956','DD-MM-YYYY'),'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-bt7_ZKFMU8UOOjlq-GYF5P0iNJVuqz9HuDI3GkLmLXDfifpy','Thomas Jeffrey Hanks is an American actor and filmmaker. Known for both his comedic and dramatic roles, he is one of the most popular and recognizable film stars worldwide, and is regarded as an American cultural icon.'),
	('Morgan', 'Freeman', 'A',  to_date('01-06-1937','DD-MM-YYYY'),'https://i.pinimg.com/originals/91/c1/61/91c161020022edecc008eb83d248cafb.jpg','Morgan Freeman is an American actor, director, and narrator. Noted for his distinctive deep voice, Freeman is known for his various roles in a wide variety of film genres'),
	('Will', 'Smith', 'A',  to_date('25-09-1968','DD-MM-YYYY'),'https://static.wikia.nocookie.net/id4/images/7/79/Will_Smith.jpg/revision/latest/top-crop/width/360/height/450?cb=20210129212321','Willard Carroll Smith Jr. is an American actor, rapper, and film producer. Smith has been nominated for five Golden Globe Awards and two Academy Awards, and has won four Grammy Awards.'),
	('Harrison', 'Ford', 'A',  to_date('13-07-1942','DD-MM-YYYY'),'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS77p6ogwyz9a8J2BkxfXhuFN9HZ2cMQOQsUZbJL02Q2oWxRO6z','Harrison Ford is an American actor. As of 2020, the U.S. domestic box-office grosses of his films total over $5.4 billion with worldwide grosses surpassing $9.3 billion, placing him at No. 7 on the list of highest-grossing domestic box office stars of all time.'),
	('Robert', 'Downey Jr.', 'A',  to_date('04-04-1965','DD-MM-YYYY'),'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8NG8tRjukxvrSbGk2CfWU_aw9yUjpnFqbAoORbc7lZityM2nl','Robert John Downey Jr. is an American actor and producer. His career has been characterized by critical and popular success in his youth, followed by a period of substance abuse and legal troubles, before a resurgence of commercial success later in his career. '),
	('Scarlett', 'Johansson','A', to_date('22-11-1984','DD-MM-YYYY'),'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3k7WTbWsb6Kn6NRCuUHUsnA3eIaL0yxcwTYN6s0NU663-gvym','Scarlett Ingrid Johansson is an American actress. She was the world''s highest-paid actress in 2018 and 2019, and has featured multiple times on the Forbes Celebrity 100 list. ');


insert into users(name,surname,email,username,password) values
	('Petar','Partaloski','partaloski@wedm.com','partaloski','Petar123!'),
	('Martin','Nikolov','nikolov@wedm.com','nikolov','Martin456#'),
	('John','Doe','doeJohn@wedm.com','doeJohn','John1234$'),
	('Gorgi','Gorgiev','gorgiev@yahoo.com','gorgi','Gorgi121!'),
    ('Sime','Stefanovski','simce@google.com','simjon','Sim121312!');



insert into movies(title, description, image_url, airing_date, imdb_rating, director_id) values
	('Iron Man',
	'After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.', 
	'https://m.media-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_.jpg', 
	to_date('02-05-2008','DD-MM-YYYY'),
	7.9,
	1),
	('Inferno',
	'When Robert Langdon wakes up in an Italian hospital with amnesia, he teams up with Dr. Sienna Brooks, and together they must race across Europe against the clock to foil a deadly global plot.',
	'https://m.media-amazon.com/images/M/MV5BMTUzNTE2NTkzMV5BMl5BanBnXkFtZTgwMDAzOTUyMDI@._V1_.jpg',
	to_date('20-10-2016','DD-MM-YYYY'),
	6.2,
	2),
	('I Am Legend',
	'Robert Neville, a scientist, is the last human survivor of a plague in the whole of New York. He attempts to find a way to reverse the effects of the man-made virus by using his own immune blood.',
	'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQHox76WNKeFcPGLKO6189r3pgqYCl8diSVwcUZgGXdYmaU9oCU',
	to_date('14-12-2007','DD-MM-YYYY'),
	7.2,
	3);


insert into genres (genre_type) values
	('Action'),
	('Adventure'),
	('Romance'),
	('Sci-Fi'),
	('Horror'),
	('Animated'),
	('Drama'),
	('Comedy'),
	('Crime'),
	('Documentary'),
	('Fantasy');

insert into movie_rates (movie_id, user_id, reason, stars_rated) values
	(1,1,'Movie was very good, a very good cinema experience with good audio spatial effects and very good story telling, one of the best movies from 2008!',10),
	(2,2,'Inferno, where Tom Hanks acts, was very unpredictable, a very good movie with many twists and turns!',9),
	(3,1,'I am a legend is such a great movie, especially for someone like me who loves post-apocalyptic scenarios, a clear 10 from me!',10),
	(3,3,'This movie had me on the edge of my seat for the whole duration of it! I loved it!',10);

insert into movie_likes(movie_id, user_id) values
	(1,5),
	(3,2),
	(3,1);

insert into movie_genres(movie_id, genre_id) values
	(1,1),(1,4),(1,11),
	(2,1),(2,2),(2,7),
	(3,1),(3,2),(3,4),(3,5);

insert into user_genres(user_id, genre_id) values
	(1,1),
	(1,2),
	(3,4),
	(3,6),
	(2,8),
	(4,1),
	(2,2),
	(4,5),
	(4,8);

insert into movie_actors(movie_id, actor_id) values
	(1,8),
	(2,4),
	(3,6);



insert into person_rates(person_id, user_id,reason, stars_rated) values
	(1,2,'You cannot hate this guy, he is one of the best directors the movie industry has ever seen, he can do wonders on every set he is at', 10),
	(4,3,'Tom Hanks is one of those classic movie actors, comes in on a set and does a great job, I actually met him and he seemed so kind, love all of his movies!', 9),
	(6,2,'I love comedies, and when I see Will Smith in one of them, I just know that it''s going to be a good one! Love this guy!', 10);


	
insert into discussions(type, text, title, date, user_id,movie_id,person_id) values
	('M', 'Do you guys think that there is a possibility of there ever being a sequel to this movie come out in the future? I loved the movie since it always had me on the edge of my seat and I love good post-apocalyptic movies, and I loved this one so I was thinking how it would be if a second one were to come out...',
	'Is there a possibility of us seeing "I Am Legend 2"?',
	current_date - interval '3 days',
	3,3,null),
	('P', 'I saw an article saying that Tom Hanks''s next movie would be the last one he''ll make and then he will just be gone, retired... Is this true, I cannot imagine the movie screens without him being on there...',
	'Is Tom Hanks retiring?',
	current_date - interval '2 days',
	4,null,4),
	('M', 'When you look at it, the 2008''s Iron Man is the best movie from the franchise that has ever came out, the thrill I get when watching that specific movie is just crazy, I don''t think we will ever get to see a movie that great once again, do you guys think the same too?',
	'Best movie of the Iron Man franchise?',
	current_date - interval '1 day',
	5,1,null),
	('P', 'Tarantino''s early films have made him a hero of 21st Century filmmaking. His unapologetic repetition of themes, skilled use of the camera to create highly tense moments, and his preference for morally ambiguous characters allows fans to instantly recognise the man in his films. This guy is awesome, he walks in on a set and basically does wonders!',
	'This guy is one of the best directors ever!',
	current_date - interval '4 days',
	2,null,1),
	('M', 'Why don''t people like this movie? I loved watching it and so did my family, we were watching it and we were all so intrigued by what came next, the suspense and all of that, the twists and turns this movie has, why is it rated that low?',
	'Inferno is a great movie and I don''t understand why people don''t like it, or that''s what IMDB is telling us by the visible rating',
	current_date - interval '5 days',
	3,2,null),
	('P', 'I want to watch some movies where Morgan Freeman acts in, which ones are the classics that you guys would recommend?',
	'Morgan Freeman - movie recommendations?',
	current_date,
	4,null,5);





insert into replies(discussion_id, text, date, user_id) values
	(6, 'I would suggest watching Bruce Almighty, he has such an important and funny role in that movie, I was having so much fun with it, as someone who loves comedies', current_date,4),
	(6, 'Going In Style is a good movie, if you like comedies, where Morgan has a bigger role and is one of the main characters, very fun movie overall! I also like the previous user''s suggestion, that movie he suggested is very cool too!', current_date,2),
	(1, 'If they decided to do one I don''t think it would match our expecations and my expectations for a movie of that kind if it comes out in the current year are pretty low, I still don''t think it would live up to my expectations, that movie was very good for its time, but not in the current day sadly :(', current_date,3);


