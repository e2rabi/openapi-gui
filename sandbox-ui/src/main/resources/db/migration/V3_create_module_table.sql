drop table if exists module cascade ;
create table module (
    version integer not null,
    visibility boolean not null,
    created timestamp(6),
    id bigint generated by default as identity,
    last_modified timestamp(6),
    solution_id bigint,
    color varchar(255),
    created_by varchar(255),
    description varchar(255),
    enabled varchar(255),
    image varchar(255),
    last_modified_by varchar(255),
    name varchar(255),
    primary key (id)
);

