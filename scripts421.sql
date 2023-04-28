/*С прошлых уроков у нас есть две таблицы: Student и Faculty. Необходимо для них создать следующие ограничения:

- Возраст студента не может быть меньше 16 лет.
- Имена студентов должны быть уникальными и не равны нулю.
- Пара “значение названия” - “цвет факультета” должна быть уникальной.
- При создании студента без возраста ему автоматически должно присваиваться 20 лет.*/

ALTER TABLE Student add CONSTRAINT age CHECK (age >= 0);

ALTER TABLE Student ADD PRIMARY KEY (name);

ALTER TABLE Faculty add CONSTRAINT name_color_unique UNIQUE (name, color);

ALTER TABLE Student alter COLUMN age SET DEFAULT 20;