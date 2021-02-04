DROP TABLE IF EXISTS db_chat;
DROP TABLE IF EXISTS db_massage;
DROP TABLE IF EXISTS db_user;
DROP TABLE IF EXISTS db_chat_user;

CREATE TABLE db_user
(
    user_id integer not null,
    user_name varchar(300),
    PRIMARY KEY (user_id)
);

CREATE TABLE db_chat
(
    chat_id integer not null,
    chat_name varchar(300),
    created_at Date,
    PRIMARY KEY (chat_id)
);

CREATE TABLE db_massage
(
    massage_id integer not null,
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
    id integer not null,
    chat_id integer not null,
    user_id integer not null,
    PRIMARY KEY (id),
    FOREIGN KEY (chat_id) REFERENCES db_chat(chat_id),
    FOREIGN KEY (user_id) REFERENCES db_user(user_id)
);









