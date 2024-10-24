drop table if exists product cascade ;
create table product (
    version integer not null,
    visibility boolean not null,
    created timestamp(6),
    id bigint generated by default as identity,
    last_modified timestamp(6),
    workspace_id bigint,
    color varchar(255),
    created_by varchar(255),
    description varchar(255),
    enabled boolean not null,
    last_modified_by varchar(255),
    name varchar(255),
    primary key (id)
);
