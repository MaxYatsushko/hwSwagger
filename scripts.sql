SELECT * from Student;

SELECT * from Faculty;
/*1. Получить всех студентов, возраст которых находится между 10 и 20 (можно подставить любые числа, главное, чтобы нижняя граница была меньше верхней).
2. Получить всех студентов, но отобразить только список их имен.
3. Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).
4. Получить всех студентов, у которых возраст меньше идентификатора.
5. Получить всех студентов упорядоченных по возрасту.*/

SELECT distinct stud.name from Student as stud;

SELECT stud.name from Student as stud;

SELECT * from Student as stud where stud.name like '%i%';
insert into Student (id, name, age, faculty_id) VALUES (100, 'Testovich', 18, 3);

update Student  set faculty_id = 3 where id < 4 ;
update Student  set faculty_id = 4 where (id >= 4) and (id<12) ;
update Student  set faculty_id = 5 where (id >= 12) and (id<20) ;

SELECT * from Student as stud where stud.age < stud.id;

SELECT * from Student as stud order by stud.age asc;