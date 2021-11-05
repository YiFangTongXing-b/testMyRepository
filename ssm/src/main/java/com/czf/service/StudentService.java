package com.czf.service;

import com.czf.domain.Student;

import java.util.List;

public interface StudentService {

    int addStudent(Student student);
    List<Student> findStudents();
}
