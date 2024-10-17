create table doctors(
  id bigint not null auto_increment,
  name varchar(100) not null,
  email varchar(100) unique not null,
  crm varchar(6) unique not null,
  phone varchar(11) unique not null,
  specialty varchar(50) not null,
  active TINYINT(1) default 1,
  created_at timestamp default CURRENT_TIMESTAMP,
  fk_administrator_id bigint not null,

  PRIMARY key(id),
  FOREIGN key(fk_administrator_id) references administrators(id)
);