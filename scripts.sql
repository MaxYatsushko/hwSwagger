/*1. Получить всех студентов, возраст которых находится между 10 и 20 (можно подставить любые числа, главное, чтобы нижняя граница была меньше верхней).
2. Получить всех студентов, но отобразить только список их имен.
3. Получить всех студентов, у которых в имени присутствует буква «О» (или любая другая).
4. Получить всех студентов, у которых возраст меньше идентификатора.
5. Получить всех студентов упорядоченных по возрасту.*/

SELECT * from Student;


SELECT * from Student as stud where stud.age between 10 and 20;

SELECT stud.name from Student as stud;


SELECT * from Student as stud where stud.name like '%o%';

SELECT * from Student as stud where stud.age < stud.id;

SELECT * from Student as stud order by stud.age asc;

select * from Avatar;


/*select top 5 * from Student order by id desc; не поддерживает метод top*/
