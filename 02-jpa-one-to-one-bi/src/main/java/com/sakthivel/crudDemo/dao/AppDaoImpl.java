package com.sakthivel.crudDemo.dao;

import com.sakthivel.crudDemo.entity.Instructor;
import com.sakthivel.crudDemo.entity.InstructorDetail;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AppDaoImpl implements AppDAO {

    EntityManager entityManager;

    @Autowired
    public AppDaoImpl(EntityManager theEntityManager)
    {
        this.entityManager = theEntityManager;
    }
    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class,theId);
    }

    @Override
    public void deleteInstructorById(int theId) {

        Instructor tempInstructor = entityManager.find(Instructor.class,theId);
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return null;
    }
}
