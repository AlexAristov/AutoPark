CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY ,
    first_name VARCHAR(30),
    last_name VARCHAR(30)
);

INSERT INTO "user"(first_name, last_name) VALUES ('Alex', 'Aristov');
INSERT INTO "user"(first_name, last_name) VALUES ('Petr', 'Petrov');
INSERT INTO "user"(first_name, last_name) VALUES ('Anton', 'Rogov');


CREATE TABLE IF NOT EXISTS "car" (
    id SERIAL PRIMARY KEY ,
    model VARCHAR(30),
    owner_id INTEGER REFERENCES "user"(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO "car"(model, owner_id) VALUES ('FOCUS', 1);
INSERT INTO "car"(model, owner_id) VALUES ('CAMRY', 2);
INSERT INTO "car"(model, owner_id) VALUES ('VESTA', 1);
INSERT INTO "car"(model, owner_id) VALUES ('CROSS', null);


CREATE TABLE IF NOT EXISTS "book" (
      id SERIAL PRIMARY KEY ,
      title VARCHAR(30),
      description VARCHAR(256)
);

INSERT INTO "book"(title, description) VALUES ('1 book', '1 description');
INSERT INTO "book"(title, description) VALUES ('2 book', '2 description');
INSERT INTO "book"(title, description) VALUES ('3 book', '3 description');
INSERT INTO "book"(title, description) VALUES ('4 book', '4 description');


CREATE TABLE IF NOT EXISTS "user_book" (
    id SERIAL PRIMARY KEY ,
    user_id INTEGER REFERENCES "user" ON DELETE CASCADE ON UPDATE CASCADE,
    book_id INTEGER REFERENCES "book" ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO "user_book"(user_id, book_id) VALUES (1, 1);
INSERT INTO "user_book"(user_id, book_id) VALUES (1, 2);
INSERT INTO "user_book"(user_id, book_id) VALUES (2, 1);
INSERT INTO "user_book"(user_id, book_id) VALUES (3, 4);