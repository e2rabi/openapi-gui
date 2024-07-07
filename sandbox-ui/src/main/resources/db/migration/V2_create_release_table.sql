drop table if exists release cascade ;
create table release (
    version integer not null,
    visibility boolean not null,
    created timestamp(6),
    id bigint generated by default as identity,
    last_modified timestamp(6),
    product_id bigint,
    color varchar(255),
    created_by varchar(255),
    description varchar(255),
    enabled varchar(255),
    last_modified_by varchar(255),
    name varchar(255),
    primary key (id)
);
