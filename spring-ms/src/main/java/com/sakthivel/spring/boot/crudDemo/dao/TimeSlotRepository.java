package com.sakthivel.spring.boot.crudDemo.dao;

import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.entity.Room;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot,Integer> {

    public List<TimeSlot> findByRoomsAndDateAndStartTimeLessThanAndEndTimeGreaterThan(Room rooms, LocalDate date, LocalTime endTime, LocalTime startTime);

}
