alter table if exists api add constraint FKf5cynu5anr99yx0nguw45jl5g foreign key (module_id) references module;
alter table if exists module add constraint FKb4wmu0i0jrfrt1ybxnvv8txb4 foreign key (solution_id) references solution;
alter table if exists product add constraint FK1mtp9sagurjdtbb1wip6osrnw foreign key (workspace_id) references workspace;
alter table if exists release add constraint FK4aopcer6xfqrjas12e8xwu2g1 foreign key (product_id) references product;
alter table if exists role_authority add constraint FKpduid6tx7e38l03s86446514r foreign key (authority_id) references authorities;
alter table if exists role_authority add constraint FK78r7yh1uqg30liv2n75ay99j foreign key (role_id) references roles;
alter table if exists solution add constraint FK4ln641thhajd02d0b98vl9tbl foreign key (release_id) references release;
alter table if exists user_role add constraint FKt7e7djp752sqn6w22i6ocqy6q foreign key (role_id) references roles;
alter table if exists user_role add constraint FKj345gk1bovqvfame88rcx7yyx foreign key (user_id) references users;
ALTER TABLE users ADD CONSTRAINT fk_user_workspace FOREIGN KEY (workspace_id) REFERENCES workspace(id);
/*alter table if exists users add constraint FKibojclo9rb1sdyjthplhypauy foreign key (workspace_id) references workspace; */