create table patients(
  id bigint auto_increment PRIMARY KEY,
  name varchar(100) not null,
  email varchar(100) not null unique,
  cpf varchar(11) not null unique,
  phone varchar(11) not null unique,
  active tinyint(1) default 1,
  created_at timestamp default CURRENT_TIMESTAMP,
  fk_address_id bigint not null unique,
  fk_administrator_id bigint not null,

  FOREIGN key(fk_address_id) references addresses(id),
  FOREIGN key(fk_administrator_id) references administrators(id)
)