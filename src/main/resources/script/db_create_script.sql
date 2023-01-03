CREATE USER app_user WITH ENCRYPTED PASSWORD 'Postgres123';

create database users;
GRANT ALL ON DATABASE users TO postgres;

CREATE schema IF NOT EXISTS users_scheme AUTHORIZATION app_user;
CREATE EXTENSION "uuid-ossp";

-- drop table if exists users_scheme.users;
CREATE TABLE if not exists users_scheme.users (
    ID UUID DEFAULT gen_random_uuid(),
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    login VARCHAR(250) NOT NULL,
    dt_deleted timestamp,
    CONSTRAINT pk_users PRIMARY KEY (ID),
    UNIQUE(login)
);


-- drop table if exists users_scheme.subscription;
CREATE TABLE if not exists users_scheme.subscription (
    ID UUID DEFAULT gen_random_uuid(),
    user_id UUID NOT null REFERENCES users_scheme.users (ID) ON DELETE RESTRICT,
    subscript_user_id UUID NOT null REFERENCES users_scheme.users (ID) ON DELETE RESTRICT,
    dt_start timestamp,
    dt_end timestamp,
    CONSTRAINT pk_subscription PRIMARY KEY (ID)
);

-- drop table if exists users_scheme.news;
CREATE TABLE if not exists users_scheme.news (
    ID UUID DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users_scheme.users (ID) ON DELETE RESTRICT,
    subject VARCHAR(250),
    text VARCHAR(2000) NOT NULL,
    dt_create timestamp,
    CONSTRAINT pk_news PRIMARY KEY (ID)
);

-- drop table if exists users_scheme.news_subscription;
CREATE TABLE if not exists users_scheme.news_subscription (
    subscription_id UUID NOT NULL REFERENCES users_scheme.subscription (ID) ON DELETE RESTRICT,
    news_id UUID NOT NULL REFERENCES users_scheme.news (ID) ON DELETE RESTRICT,
    CONSTRAINT pk_news_subscription PRIMARY KEY (subscription_id, news_id)
);

-- drop table if exists users_scheme.companies;
CREATE TABLE if not exists users_scheme.companies (
    ID UUID DEFAULT gen_random_uuid(),
    name VARCHAR(250) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    CONSTRAINT pk_companies PRIMARY KEY (ID),
    UNIQUE(phone),
    UNIQUE(name)
);

-- drop table if exists users_scheme.addresses;
CREATE TABLE if not exists users_scheme.addresses (
    ID UUID DEFAULT gen_random_uuid(),
    city VARCHAR(250) NOT NULL,
    street VARCHAR(250) NOT NULL,
    house_number VARCHAR(4) NOT null,
    flat_number integer CHECK (flat_number > 0),
    CONSTRAINT pk_addresses PRIMARY KEY (ID),
    UNIQUE(city)
);

-- drop table if exists users_scheme.companies_addresses;
CREATE TABLE if not exists users_scheme.companies_addresses (
    companies_ID UUID NOT null REFERENCES users_scheme.companies (ID) ON DELETE CASCADE,
    addresses_ID UUID NOT null REFERENCES users_scheme.addresses (ID) ON DELETE RESTRICT,
    CONSTRAINT pk_companies_addresses PRIMARY KEY (companies_ID, addresses_ID)
);

INSERT INTO users_scheme.users (id, first_name, last_name, login)
VALUES ('f899e37e-885d-4d72-bc58-4da4ce3bf750', 'Владимир', 'Ленин', 'user1');
INSERT INTO users_scheme.users (id, first_name, last_name, login)
VALUES ('5a5cc272-e1e2-4340-b251-12c36877d6c5', 'Иван', 'Иваноы', 'user2');


INSERT INTO users_scheme.subscription (id, user_id, subscript_user_id, dt_start)
VALUES ('dc50d0e7-088a-4126-99a6-8bb71115e854', 'f899e37e-885d-4d72-bc58-4da4ce3bf750',
'5a5cc272-e1e2-4340-b251-12c36877d6c5', current_date);

INSERT INTO users_scheme.news (id, user_id, subject, text, dt_create)
VALUES ('cc491a38-54b3-49dc-8416-5383403048bc', 'f899e37e-885d-4d72-bc58-4da4ce3bf750',
'Тестовая новость', 'Лучшие новости', current_date);

INSERT into users_scheme.news_subscription (subscription_id, news_id)
VALUES('dc50d0e7-088a-4126-99a6-8bb71115e854', 'cc491a38-54b3-49dc-8416-5383403048bc');


INSERT INTO users_scheme.addresses (id, city,street,house_number,flat_number)
VALUES ('49c0a419-117c-48fc-80f4-bd3ce1b44a40', 'Торонто','Hasming','18',3);
INSERT INTO users_scheme.addresses (id, city,street,house_number,flat_number)
VALUES ('c900ab31-aba1-4dea-b678-4645241542fd', 'York','test','12',34);

INSERT INTO users_scheme.companies (id, name, phone)
 VALUES ('e5563b1b-a2b9-49f5-bcd7-e77405f2c377', 'ООО "Рога и копыта"', '3442342');
INSERT INTO users_scheme.companies (id, name, phone)
 VALUES ('e5563b1b-a2b9-49f5-bcd7-e77405f2c377', 'Test company', '3442342');

INSERT INTO users_scheme.companies_addresses (companies_id, addresses_id)
VALUES ('e5563b1b-a2b9-49f5-bcd7-e77405f2c377', 'c900ab31-aba1-4dea-b678-4645241542fd');

--select u.* from users_scheme.users u
--select a.* from users_scheme.addresses a  where a.id ='6e182b8e-f672-40d1-b6ae-0234a2c6201a'