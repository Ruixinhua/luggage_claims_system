drop table IF EXISTS policy;
create table policy
(
  serial_no     bigint not null,
  place_from    varchar(255),
  policy_type   varchar(255),
  place_to      varchar(255),
  validate_from datetime,
  validate_to   datetime,
  primary key (serial_no)
) engine = MyISAM;