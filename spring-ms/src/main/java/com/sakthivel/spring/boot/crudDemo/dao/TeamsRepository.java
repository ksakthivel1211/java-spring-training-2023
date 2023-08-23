package com.sakthivel.spring.boot.crudDemo.dao;

import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamsRepository extends JpaRepository<Teams,Integer> {

    List<Teams> findByTimeSlotsAndTeamId(TimeSlot timeSlot,int teamId);
}
