drop table if exists role_authority cascade;
create table role_authority (
    authority_id bigint not null,
    role_id bigint not null,
    primary key (authority_id, role_id)
);