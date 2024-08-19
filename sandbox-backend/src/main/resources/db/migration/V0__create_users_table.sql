drop table if exists users cascade;
create table users (
    account_non_expired boolean not null,
    account_non_locked boolean not null,
    credentials_non_expired boolean not null,
    enabled boolean not null,
    id bigint generated by default as identity,
    workspace_id bigint,
    address varchar(255), email varchar(255),
    first_name varchar(255), last_name varchar(255),
    password varchar(255), phone varchar(255),
    username varchar(255),
    expiry_date varchar(20),
    version integer not null,
    created timestamp(6),
    last_modified timestamp(6),
    created_by varchar(255),
    last_modified_by varchar(255),
    first_login_change_password boolean null,
    primary key (id)
);