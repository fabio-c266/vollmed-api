create table uf(
  id int auto_increment,
  acronym char(2) unique not null,
  name varchar(50) unique not null,

  primary key(id)
);

create table city(
  id int auto_increment,
  name varchar(50) unique not null,
  fk_uf_id int not null,

  primary key(id),
  FOREIGN key(fk_uf_id) references uf(id)
);

create table addresses (
  id bigint auto_increment,
  cep char(8) not null,
  street varchar(100) not null,
  neighborhood varchar(100) not null,
  complement varchar(80),
  number int,
  fk_city_id int not null,

  primary key(id),
  FOREIGN key(fk_city_id) references city(id)
)