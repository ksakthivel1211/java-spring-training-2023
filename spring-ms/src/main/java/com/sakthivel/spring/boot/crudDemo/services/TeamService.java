package com.sakthivel.spring.boot.crudDemo.services;

import com.sakthivel.spring.boot.crudDemo.dao.TeamsRepository;
import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import com.sakthivel.spring.boot.crudDemo.respose.EmployeeResponse;
import com.sakthivel.spring.boot.crudDemo.respose.TeamResponse;
import com.sakthivel.spring.boot.crudDemo.respose.TimeSlotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private TeamsRepository teamsRepository;

    @Autowired
    public TeamService(TeamsRepository theTeamRepository){teamsRepository=theTeamRepository;}

    /**
     * getAllTeams method returns all the existing teams
     * @return - returns List of all teams object
     */
    public List<TeamResponse> getAllTeams()
    {
        List<Teams> theTeams = teamsRepository.findAll();
        List<TeamResponse> teamResponses = theTeams.stream().map(theTeam->{
            return getTeamResponse(theTeam);
        }).collect(Collectors.toList());


        return teamResponses;
    }

    /**
     * getTeamById method returns team details (Team object) whose id is given
     * @param teamsId - team id with type int passed as parameter whose team details should be found
     * @return returns the employee Object of the given Employee id
     */
    public TeamResponse getTeamById(int teamsId)
    {
        Optional<Teams> result = teamsRepository.findById(teamsId);

        Teams theTeams = null;
        if(result.isPresent())
        {
            theTeams=result.get();
            return getTeamResponse(theTeams);
        }
        else {
            throw new RuntimeException("Employee id not found :"+teamsId);
        }

    }

    /**
     * addTeams method adds the team details to the DB using the save method in team repository
     * @param theTeams - Team object type passed as parameter which is added to the DB
     * @return returns the Team object which has been saved in the DB
     */
    public String addTeams(Teams theTeams)
    {
        theTeams.setTeamId(0);

        Teams dbTeams = teamsRepository.save(theTeams);
        return "Teams details updated";
    }

    /**
     * updateTeam method updates the existing team details in the DB by fetching the Existing team details using the team id and saving the new team object to the DB again
     * @param theTeams - parameter of type Team is passed as parameter which is the new team object to be updated in the DB
     * @param teamsId - team id is a parameter of type int which determines which team object to be retrieved to be updated
     * @return - returns the Team object which has been saved in the DB
     */
    public String updateTeams(Teams theTeams,int teamsId)
    {
        theTeams.setTeamId(teamsId);
        Teams dbTeams = teamsRepository.save(theTeams);
        return "Teams details updated on team id : "+teamsId;
    }

    /**
     * deleteTeams method deletes the team instance from the DB by retrieving the team details using the team id and deleting it using the deleteById method in the team repository
     * @param teamsId - team id is a parameter of type int which determines which team object to be retrieved to be deleted
     * @return - returns string message whether the team instance is deleted or not
     */
    public String deleteTeams(int teamsId)
    {
        Optional<Teams> result = teamsRepository.findById(teamsId);

        if(result.isPresent())
        {
            teamsRepository.deleteById(teamsId);
        }
        else {
            throw new RuntimeException("Team id not found :"+teamsId);
        }
        return "Deleted employee of id - "+ teamsId;
    }

    public TeamResponse getTeamResponse(Teams theTeams)
    {
        TeamResponse teamResponse = new TeamResponse(theTeams.getTeamId(),theTeams.getTeamName(),theTeams.getTeamCount());

        List<Employee> teamEmployees = theTeams.getEmployees();
        List<EmployeeResponse> employeeResponses = teamEmployees.stream().map(theEmployee->{
            EmployeeResponse returnEmployee = new EmployeeResponse(theEmployee.getEmployeeId(),theEmployee.getFirstName(),theEmployee.getLastName(),theEmployee.getEmail());
            return returnEmployee;
        }).collect(Collectors.toList());


        teamResponse.setEmployees(employeeResponses);

        List<TimeSlot> teamTimeSlots = theTeams.getTimeSlots();
        List<TimeSlotResponse> timeSlotResponses = teamTimeSlots.stream().map(theTimeSlot->{
            TimeSlotResponse returnTimeSlot = new TimeSlotResponse(theTimeSlot.getTimeSlotId(),theTimeSlot.getStartTime(),theTimeSlot.getEndTime(),theTimeSlot.getDate(),theTimeSlot.getBooked(),theTimeSlot.getDescription());
            return returnTimeSlot;
        }).collect(Collectors.toList());

        teamResponse.setTimeSlots(timeSlotResponses);

        return teamResponse;
    }

}
