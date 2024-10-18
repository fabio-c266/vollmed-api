create table consultations(
  id bigint auto_increment primary key,
  fk_doctor_id bigint not null,
  fk_patient_id bigint not null,
  consultation_date datetime not null,
  status VARCHAR(12) not null,
  created_at timestamp default CURRENT_TIMESTAMP not null,

  foreign key(fk_doctor_id) references doctors(id),
  foreign key(fk_patient_id) references patients(id)
);