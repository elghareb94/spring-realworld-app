
create table users (
    id int  primary key AUTO_INCREMENT,
    username varchar(255) UNIQUE,
    password varchar(255),
    email varchar(255) UNIQUE,
    bio text,
    image_url varchar(255)
);

create table follows (
    id int primary key AUTO_INCREMENT,
    user_id int not null,
    follow_id int not null,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (follow_id) REFERENCES users(id)
);

create table articles (
	id int primary key AUTO_INCREMENT,
	user_id int ,
	slug varchar(255) UNIQUE,
	title varchar(255),
	description text,
	body text,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

create table tags (
	id int primary key AUTO_INCREMENT,
    article_id int ,
	name varchar(255) not null
    FOREIGN KEY (article_id) REFERENCES articles(id)
);


create table comments (
	id int  primary key AUTO_INCREMENT,
    body text,
	article_id int ,
	user_id int ,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

	FOREIGN KEY (article_id) REFERENCES articles(id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

create table users_articles_favorites (
    article_id int not null AUTO_INCREMENT,
    user_id int not null,
    primary key(article_id, user_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (article_id) REFERENCES articles(id)
);
