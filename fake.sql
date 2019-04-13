drop table if exists claim;
drop table if exists permission;
drop table if exists role;
drop table if exists role_permissions;
drop table if exists role_users;
drop table if exists user;
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
create table role
(
  id        integer not null,
  describes varchar(255),
  role      varchar(255),
  primary key (id)
) engine = MyISAM;
create table role_permissions
(
  roles_id       integer not null,
  permissions_id bigint  not null,
  primary key (roles_id, permissions_id),
  FOREIGN KEY (roles_id) REFERENCES role (id),
  FOREIGN KEY (permissions_id) REFERENCES permission (id)
) engine = MyISAM;
create table role_users
(
  roles_id integer not null,
  users_id bigint  not null,
  primary key (roles_id, users_id),
  FOREIGN KEY (users_id) REFERENCES user (id),
  FOREIGN KEY (roles_id) REFERENCES role (id)
) engine = MyISAM;
create table user
(
  id            bigint      not null auto_increment,
  email_address varchar(50) not null unique,
  enabled       bit,
  first_name    varchar(20) not null,
  last_name     varchar(20) not null,
  login_date    datetime,
  nickname      varchar(20) not null unique,
  passport      varchar(20) not null,
  password      varchar(20) not null,
  phone_number  varchar(20) not null,
  register_date datetime    not null,
  primary key (id)
) engine = MyISAM;

# create table claim (serial_no bigint not null, billing_address varchar(50) not null, customer_id bigint not null, details varchar(50) not null, employee_id bigint, flight_no varchar(20) not null, lost_luggage varchar(50), date datetime, primary key (serial_no)) engine=MyISAM;
# create table permission (id bigint not null, describes varchar(255), permission varchar(255), url varchar(255), primary key (id)) engine=MyISAM;
# create table role (id integer not null, describes varchar(255), role varchar(255), primary key (id)) engine=MyISAM;
# create table role_permissions (roles_id integer not null, permissions_id bigint not null, primary key (roles_id, permissions_id), FOREIGN KEY (roles_id) REFERENCES role(id), FOREIGN KEY (permissions_id) REFERENCES permission(id)) engine=MyISAM;
# create table user (id bigint not null auto_increment, email_address varchar(50) not null unique, first_name varchar(20) not null, last_name varchar(20) not null, login_date datetime, nickname varchar(20) not null unique, passport varchar(20) not null, password varchar(20) not null, phone_number varchar(20) not null, register_date datetime not null, role integer, primary key (id)) engine=MyISAM;
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
