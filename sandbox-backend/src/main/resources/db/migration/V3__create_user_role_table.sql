drop table if exists user_role cascade;
create table user_role (
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id)
);