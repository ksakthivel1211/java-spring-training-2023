package com.sakthivel.spring.boot.crudDemo.dao;

import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee , Integer> {

}
