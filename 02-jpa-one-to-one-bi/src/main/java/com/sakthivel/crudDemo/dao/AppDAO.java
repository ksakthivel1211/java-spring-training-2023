package com.sakthivel.crudDemo.dao;

import com.sakthivel.crudDemo.entity.Instructor;
import com.sakthivel.crudDemo.entity.InstructorDetail;

public interface AppDAO {

    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorById(int theId);

    InstructorDetail findInstructorDetailById(int theId);
}
