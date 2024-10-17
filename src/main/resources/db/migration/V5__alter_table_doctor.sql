alter table doctors
add column fk_address_id bigint not null unique;

ALTER TABLE doctors
ADD FOREIGN KEY (fk_address_id) REFERENCES addresses(id);