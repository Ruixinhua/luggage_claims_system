drop table if exists claim;
drop table if exists permission;
drop table if exists permission_roles;
drop table if exists role_permissions;
drop table if exists role;
drop table if exists user;
drop table if exists user_roles;
create table claim
(
  serial_no       bigint      not null,
  billing_address varchar(50) not null,
  customer_id     bigint      not null,
  date            datetime,
  details         varchar(50) not null,
  employee_id     bigint,
  flight_no       varchar(20) not null,
  lost_luggage    varchar(50),
  primary key (serial_no)
) engine = MyISAM;
create table permission
(
  id         bigint not null,
  describes  varchar(255),
  permission varchar(255),
  url        varchar(255),
  primary key (id)
) engine = MyISAM;
create table role_permissions
(
  permissions_id bigint  not null,
  roles_id       integer not null,
  primary key (permissions_id, roles_id)
) engine = MyISAM;
create table role
(
  id        integer not null auto_increment,
  describes varchar(255),
  role      varchar(255),
  primary key (id)
) engine = MyISAM;
create table user
(
  id            bigint       not null auto_increment,
  email_address varchar(50)  not null,
  enabled       bit,
  first_name    varchar(20)  not null,
  last_name     varchar(20)  not null,
  login_date    datetime,
  nickname      varchar(20)  not null,
  passport      varchar(20)  not null,
  password      varchar(100) not null,
  phone_number  varchar(20)  not null,
  register_date datetime     not null,
  role          INTEGER,
  primary key (id)
) engine = MyISAM;
create table user_roles
(
  users_id bigint  not null,
  roles_id integer not null,
  primary key (users_id, roles_id)
) engine = MyISAM;
alter table user
  add constraint UK_d0ar1h7wcp7ldy6qg5859sol6 unique (email_address);
alter table user
  add constraint UK_n4swgcf30j6bmtb4l4cjryuym unique (nickname);
# alter table permission_roles
#   add constraint FK9k4j9myvlxs8w8omv4awtpcpo foreign key (roles_id) references role (id);
# alter table permission_roles
#   add constraint FK9lqx45njk0pt5lcux4lqewijk foreign key (permissions_id) references permission (id);
alter table user_roles
  add constraint FKj9553ass9uctjrmh0gkqsmv0d foreign key (roles_id) references role (id);
alter table user_roles
  add constraint FK7ecyobaa59vxkxckg6t355l86 foreign key (users_id) references user (id);
INSERT INTO role (id, describes, role)
VALUES (1, "The role is employee", "EMPLOYEE");
INSERT INTO role (id, describes, role)
VALUES (2, "The role is customer", "CUSTOMER");
INSERT INTO permission(id, describes, permission, url)
VALUES (1, "All employee page can be accessed", "P_EMPLOYEE", "/employee/**");
INSERT INTO permission(id, describes, permission, url)
VALUES (2, "All customer page can be accessed", "P_CUSTOMER", "/customer/**");
INSERT INTO role_permissions(roles_id, permissions_id)
VALUES (1, 1);
INSERT INTO role_permissions(roles_id, permissions_id)
VALUES (2, 2);
# INSERT INTO permission_roles(roles_id, permissions_id)
# VALUES (1, 1);
# INSERT INTO permission_roles(roles_id, permissions_id)
# VALUES (2, 2);
# insert into user (email_address, enabled, first_name, last_name, login_date, nickname, passport, password, phone_number,
#                   register_date, role)
# values ("rxhjxja@gmail.com", false, "Dairui", "Liu", null, "ruixinhua", "E89211241", "a12345test", "86-17338129825",
#         "2019-04-14 13:26:49.0",
#         1);
# insert into user (email_address, enabled, first_name, last_name, login_date, nickname, passport, password, phone_number,
#                   register_date, role)
# values ("amazing@liu.com", false, "amazing", "Liu", null, "amazing", "E21332112", "12345test", "86-12345678911",
#         "2019-04-14 13:26:49.0",
#         1);