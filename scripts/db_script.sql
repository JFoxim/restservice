CREATE TABLE if not exists users_scheme.companies (
    ID UUID DEFAULT gen_random_uuid(),
    name VARCHAR(250) NOT NULL,
    phone VARCHAR(11) NOT NULL, 
    CONSTRAINT pk_companies PRIMARY KEY (ID),
    UNIQUE(phone),
    UNIQUE(name)
);

CREATE TABLE if not exists users_scheme.addresses (
    ID UUID DEFAULT gen_random_uuid(),
    city VARCHAR(250) NOT NULL,
    street VARCHAR(250) NOT NULL,
    house_number VARCHAR(4) NOT null,
    flat_number integer CHECK (flat_number > 0),
    CONSTRAINT pk_addresses PRIMARY KEY (ID),
    UNIQUE(city)
);

CREATE TABLE if not exists users_scheme.companies_addresses (
    companies_ID UUID NOT null REFERENCES users_scheme.companies (ID) ON DELETE CASCADE,
    addresses_ID UUID NOT null REFERENCES users_scheme.addresses (ID) ON DELETE RESTRICT,
    CONSTRAINT pk_companies_addresses PRIMARY KEY (companies_ID, addresses_ID)
);

CREATE TABLE if not exists users_scheme.companies_addresses (
    companies_ID UUID NOT null REFERENCES users_scheme.companies (ID) ON DELETE CASCADE,
    addresses_ID UUID NOT null REFERENCES users_scheme.addresses (ID) ON DELETE RESTRICT,
    CONSTRAINT pk_companies_addresses PRIMARY KEY (companies_ID, addresses_ID)
);

CREATE TABLE if not exists users_scheme.users (
    ID UUID DEFAULT gen_random_uuid(),
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    login VARCHAR(250) NOT NULL,
    dt_deleted timestamp,
    CONSTRAINT pk_users PRIMARY KEY (ID),
    UNIQUE(login)    
);
