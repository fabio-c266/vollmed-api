create table administrators(
  id bigint not null auto_increment,
  name varchar(100) not null,
  email varchar(100) unique not null,
  password_hashed varchar(255) not null,
  created_at timestamp default CURRENT_TIMESTAMP,

  PRIMARY KEY(id)
);