CREATE USER app_user WITH ENCRYPTED PASSWORD 'Postgres123';

create database users;
GRANT ALL ON DATABASE users TO postgres;

CREATE schema IF NOT EXISTS users_scheme AUTHORIZATION app_user;
CREATE EXTENSION "uuid-ossp";

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

create index i_address_city
on users_scheme.addresses using HASH (city);

-- drop table if exists users_scheme.users;
CREATE TABLE if not exists users_scheme.users (
    ID UUID DEFAULT gen_random_uuid(),
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250),
    patronymic VARCHAR(250),
    login VARCHAR(250) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    address_id UUID REFERENCES users_scheme.addresses (ID) ON DELETE RESTRICT,
    dt_deleted timestamp,
    CONSTRAINT pk_users PRIMARY KEY (ID),
    UNIQUE(login)
);

create index i_users_gender
on users_scheme.users(gender);

create index i_users_first_name_last_name
on users_scheme.users(first_name, last_name);

create index i_users_gender_address_id
on users_scheme.users(gender, address_id);

create index i_users_login
on users_scheme.users  using HASH (login);


-- drop table if exists users_scheme.subscription;
CREATE TABLE if not exists users_scheme.subscription (
    ID UUID DEFAULT gen_random_uuid(),
    creator_user_id UUID NOT null REFERENCES users_scheme.users (ID) ON DELETE RESTRICT,
    subscriber_user_id UUID NOT null REFERENCES users_scheme.users (ID) ON DELETE restrict,
    CONSTRAINT pk_subscription PRIMARY KEY (ID)
);

-- drop table if exists users_scheme.news;
CREATE TABLE if not exists users_scheme.news (
    ID UUID DEFAULT gen_random_uuid(),
    creator_user_id UUID NOT NULL REFERENCES users_scheme.users (ID) ON DELETE RESTRICT,
    subject VARCHAR(250),
    text VARCHAR(2000) NOT NULL,
    dt_create timestamp,
    CONSTRAINT pk_news PRIMARY KEY (ID)
);

create index i_news_creator_user_id
on users_scheme.news(creator_user_id);

create index i_news_creator_subject
on users_scheme.news(subject);

-- drop table if exists users_scheme.companies;
CREATE TABLE if not exists users_scheme.companies (
    ID UUID DEFAULT gen_random_uuid(),
    name VARCHAR(250) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    CONSTRAINT pk_companies PRIMARY KEY (ID),
    UNIQUE(phone),
    UNIQUE(name)
);

-- drop table if exists users_scheme.companies_addresses;
CREATE TABLE if not exists users_scheme.companies_addresses (
    companies_ID UUID NOT null REFERENCES users_scheme.companies (ID) ON DELETE CASCADE,
    addresses_ID UUID NOT null REFERENCES users_scheme.addresses (ID) ON DELETE RESTRICT,
    CONSTRAINT pk_companies_addresses PRIMARY KEY (companies_ID, addresses_ID)
);

INSERT INTO users_scheme.addresses (id, city,street,house_number,flat_number)
VALUES ('49c0a419-117c-48fc-80f4-bd3ce1b44a40', 'Торонто','Hasming','18',3);
INSERT INTO users_scheme.addresses (id, city,street,house_number,flat_number)
VALUES ('c900ab31-aba1-4dea-b678-4645241542fd', 'Йорк','Ньютон','12',34);
INSERT INTO users_scheme.addresses (id, city,street,house_number,flat_number)
VALUES ('bdc0cdb4-8bd9-4aee-b33e-0b28aa9691b3', 'Москва','Ленина','5',5);

INSERT INTO users_scheme.users (id, first_name, last_name, patronymic, login, gender, address_id)
VALUES ('f899e37e-885d-4d72-bc58-4da4ce3bf750', 'Владимир', 'Ленин', 'Ильич' 'user1', 'муж', '49c0a419-117c-48fc-80f4-bd3ce1b44a40');
INSERT INTO users_scheme.users (id, first_name, last_name, patronymic, login, gender, address_id)
VALUES ('5a5cc272-e1e2-4340-b251-12c36877d6c5', 'Иван', 'Иванов', 'Иванович', 'user2', 'муж', 'bdc0cdb4-8bd9-4aee-b33e-0b28aa9691b3');
INSERT INTO users_scheme.users (id, first_name, last_name, patronymic, login, gender, address_id)
VALUES ('7500738a-2528-42d0-9106-55a3c8bced47', 'Мария', 'Захарова', 'Петровна', 'user3', 'жен', 'c900ab31-aba1-4dea-b678-4645241542fd');

INSERT INTO users_scheme.subscription (id, creator_user_id, subscriber_user_id)
VALUES ('dc50d0e7-088a-4126-99a6-8bb71115e854', 'f899e37e-885d-4d72-bc58-4da4ce3bf750',
'5a5cc272-e1e2-4340-b251-12c36877d6c5');

INSERT INTO users_scheme.news (id, creator_user_id, subject, text, dt_create)
VALUES ('cc491a38-54b3-49dc-8416-5383403048bc', 'f899e37e-885d-4d72-bc58-4da4ce3bf750',
'Тестовая новость', 'Лучшие новости', current_date);

INSERT INTO users_scheme.companies (id, name, phone)
 VALUES ('e5563b1b-a2b9-49f5-bcd7-e77405f2c377', 'ООО "Рога и копыта"', '3442342');
INSERT INTO users_scheme.companies (id, name, phone)
 VALUES ('d619abc6-023d-481c-a7b1-051810e5137c', 'Test company', '3466342');

INSERT INTO users_scheme.companies_addresses (companies_id, addresses_id)
VALUES ('e5563b1b-a2b9-49f5-bcd7-e77405f2c377', 'c900ab31-aba1-4dea-b678-4645241542fd');