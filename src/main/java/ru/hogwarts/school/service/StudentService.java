package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final Object flag = new Object();

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudentById(Long id) {
        logger.debug("Requesting getStudentById {}", id);
        Student localStudent = studentRepository.findById(id).get();
        logger.debug("The request of getStudentById is {} by id={}", localStudent.getName(), id);

        return localStudent;
    }
    public Student add(Student student){
        logger.debug("Requesting add id={}, name={}", student.getId(), student.getName());
        Student addStudent = studentRepository.save(student);
        logger.debug("The request of add student is id={}, name={}", addStudent.getId(), addStudent.getName());

        return addStudent;
    }

    public void delete(long id){
        logger.debug("Requesting delete id={}", id);
        studentRepository.deleteById(id);
        logger.debug("The request of delete is successful id={}", id);
    }

    public Collection<Student> getAll(){
        logger.debug("Requesting getAll");
        Collection<Student> students = studentRepository.findAll();
        logger.debug("The request of getAll is successful and received numbers={}", students.size());

        return students;
    }

    public List<Student> getAll(int age){
        logger.debug("Requesting getAll by age={}", age);
        List<Student> students = studentRepository.findAllByAge(age);
        logger.debug("The request of getAll by age is successful and received numbers={}", students.size());

        return students;
    }

    public List<Student> getAll(int minAge, int maxAge){
        logger.debug("Requesting getAll by minAge={} and maxAge={}", minAge, maxAge);
        List<Student> students = studentRepository.findAllByAgeBetween(minAge, maxAge);
        logger.debug("The request of getAll between ages is successful and received numbers={}", students.size());

        return students;
    }

    public Faculty getFaculty(Student student){
        logger.debug("Requesting getFaculty by student id={} and name={}", student.getId(), student.getName());
        Faculty faculty = student.getFaculty();
        logger.debug("The request of getFaculty by Student is successful and faculty id={} name={}", faculty.getId(), faculty.getName());

        return faculty;
    }

    public Student update(Student student){
        logger.debug("Requesting update student id={} and name={}", student.getId(), student.getName());
        Student updateStudent = studentRepository.save(student);
        logger.debug("The request of update Student is successful and student id={} name={}", updateStudent.getId(), updateStudent.getName());

        return updateStudent;
    }

    public int getNumberStudents(){
        logger.debug("Requesting getNumberStudents");
        int numberStudents = studentRepository.getNumberStudents();
        logger.debug("The request of getNumberStudents is successful and numbers={}", numberStudents);

        return numberStudents;
    }

    public int getAvgAge(){
        logger.debug("Requesting getAvgAge");
        int avgAge = studentRepository.getAvgAge();
        logger.debug("The request of getAvgAge is successful and avgAge={}", avgAge);
        return avgAge;
    }

    public List<Student> getLastFiveStudent(){
        logger.debug("Requesting getLastFiveStudent");
        List<Student> students = studentRepository.getLastFiveStudent();
        logger.debug("The request of getLastFiveStudent is successful and numbers={}", students.size());

        return students;
    }

    public List<String> getStudentsNameBeginA(){
        logger.debug("Requesting getStudentsNameBeginA");
        List<String> nameStudents = studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
        logger.debug("The request of getStudentsNameBeginA is successful and numbers={}", nameStudents.size());

        return nameStudents;
    }

    public Double getAvgAgeStream(){
        logger.debug("Requesting getAvgAgeStream");
        Double avgAge = studentRepository.findAll().stream()
                .mapToInt(student -> student.getAge()).average()
                .orElse(0.0);
        logger.debug("The request of getAvgAgeStream is successful and average age={}", avgAge);

        return avgAge;
    }

    public void printAll(){
        List<Student> students = studentRepository.findAll();

        System.out.println(students);

        printStudent(students.get(0));
        printStudent(students.get(1));

        new Thread(()-> {
                printStudent(students.get(2));
                printStudent(students.get(3));
        }
        ).start();

        new Thread(()-> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        }
        ) .start();
    }

    private void printStudent(Student student){
        System.out.println(Thread.currentThread().getName() + " " + student);
    }

    public void printAllSync(){
        List<Student> students = studentRepository.findAll();

        System.out.println(students);

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        new Thread(()-> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        }
        ).start();

        new Thread(()-> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        }
        ) .start();
    }

    private void printStudentSync(Student student){
        synchronized (flag) {
            System.out.println(Thread.currentThread().getName() + " " + student);
        }
    }
}
