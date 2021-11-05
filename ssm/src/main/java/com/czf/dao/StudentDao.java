package com.czf.dao;

import com.czf.domain.Student;

import java.util.List;

public interface StudentDao {
    int insertStudent(Student student);
    List<Student> selectStudents();
}
