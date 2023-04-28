/*В этом задании по описанию необходимо спроектировать таблицы, связи между ними и корректно определить типы данных.
  Здесь не важно, какой тип вы выберете, например, для данных, представленных в виде строки (varchar или text).
  Важно, что вы выберете один из строковых типов, а не числовых, например.
  Описание структуры: у каждого человека есть машина. Причем несколько человек могут пользоваться одной машиной.
  У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет). У каждой машины есть марка, модель и стоимость.
  Также не забудьте добавить таблицам первичные ключи и связать их.
 */

 CREATE TABLE People (
     id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
     age int NOT NULL,
     isDriveLicense BOOLEAN DEFAULT false,
     fulName varchar(200) NOT NULL,
     car_id int REFERENCES Cars (id)
 );

/*CREATE SEQUENCE cars_seq;
  id int DEFAULT cars_seq.nextval PRIMARY KEY
  id int IDENTITY(1,1) PRIMARY KEY
 */

CREATE TABLE Cars (
     id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
     brand varchar(50) NOT NULL,
     model varchar(50) NOT NULL,
     cost numeric(12,2)
);

/*Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах (достаточно получить только имя и возраст студента) школы Хогвартс
вместе с названиями факультетов.
Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.
В корне проекта создать файл scripts422.sql и поместить в него запрос.*/

SELECT stud.name, stud.age, fac.name FROM Student as stud
inner join Faculty as fac
on stud.faculty_id = fac.id
order by fac.name, stud.name;

SELECT stud.name, stud.age, ava.file_size FROM Student as stud
inner join Avatar as ava
on stud.id = ava.student_id
order by stud.name;
