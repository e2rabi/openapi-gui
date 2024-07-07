alter table if exists api add constraint FKf5cynu5anr99yx0nguw45jl5g foreign key (module_id) references module;
alter table if exists module add constraint FKb4wmu0i0jrfrt1ybxnvv8txb4 foreign key (solution_id) references solution;
alter table if exists release add constraint FK4aopcer6xfqrjas12e8xwu2g1 foreign key (product_id) references product;
alter table if exists solution add constraint FK4ln641thhajd02d0b98vl9tbl foreign key (release_id) references release;