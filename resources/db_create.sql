
DROP TABLE IF EXISTS db_chat_user CASCADE;
DROP TABLE IF EXISTS db_user CASCADE;
DROP TABLE IF EXISTS db_massage CASCADE;
DROP TABLE IF EXISTS db_chat CASCADE;

CREATE TABLE db_user
(
    user_id SERIAL UNIQUE,
    user_name varchar(300) UNIQUE ,
    PRIMARY KEY (user_id)
);

CREATE TABLE db_chat
(
    chat_id SERIAL UNIQUE,
    chat_name varchar(300) UNIQUE,
    created_at Date,
    PRIMARY KEY (chat_id)
);

CREATE TABLE db_massage
(
    massage_id SERIAL UNIQUE,
    chat_id integer not null,
    user_id integer not null,
    massage_text varchar(1000),
    created_at Date,
    PRIMARY KEY (massage_id),
    FOREIGN KEY (chat_id) REFERENCES db_chat(chat_id),
    FOREIGN KEY (user_id) REFERENCES db_user(user_id)
);



CREATE TABLE db_chat_user
(
    id SERIAL UNIQUE,
    chat_id integer not null,
    user_id integer not null,
    PRIMARY KEY (id),
    FOREIGN KEY (chat_id) REFERENCES db_chat(chat_id),
    FOREIGN KEY (user_id) REFERENCES db_user(user_id)
);





ALTER TABLE db_user ADD user_pass char(10);


INSERT INTO db_user ( user_name, user_pass) VALUES
('Админ', '1111');
INSERT INTO db_user ( user_name, user_pass) VALUES
('юзер1', '1111');


INSERT INTO db_chat ( chat_name, created_at) VALUES('answer',current_timestamp);
INSERT INTO db_chat_user ( chat_id, user_id) VALUES('1','1')
