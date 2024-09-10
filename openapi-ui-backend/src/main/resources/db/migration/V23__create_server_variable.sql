drop table if exists server_variable cascade;
create table server_variable (
                                 id bigint generated by default as identity,
                                 server_id bigint,
                                 default_value varchar(255),
                                 description varchar(255),
                                 enum_values varchar(255),
                                 name varchar(255),
                                 primary key (id)
);