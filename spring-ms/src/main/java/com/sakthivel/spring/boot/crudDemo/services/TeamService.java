package com.sakthivel.spring.boot.crudDemo.services;

import com.sakthivel.spring.boot.crudDemo.customException.BookingException;
import com.sakthivel.spring.boot.crudDemo.dao.TeamsRepository;
import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.EmployeeResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TeamResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TimeSlotResponse;
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
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
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
        try {

    }
    catch (Exception exception)
    {
        throw new BookingException(exception.getMessage());
    }
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
    public ControllerResponse addTeams(Teams theTeams)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        theTeams.setTeamId(0);
        Teams dbTeams = teamsRepository.save(theTeams);
        ControllerResponse response = new ControllerResponse("Teams details updated");
        return response;
    }

    /**
     * updateTeam method updates the existing team details in the DB by fetching the Existing team details using the team id and saving the new team object to the DB again
     * @param theTeams - parameter of type Team is passed as parameter which is the new team object to be updated in the DB
     * @param teamsId - team id is a parameter of type int which determines which team object to be retrieved to be updated
     * @return - returns the Team object which has been saved in the DB
     */
    public ControllerResponse updateTeams(Teams theTeams,int teamsId)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        theTeams.setTeamId(teamsId);
        Teams dbTeams = teamsRepository.save(theTeams);
        ControllerResponse response = new ControllerResponse("Teams details updated on team id : "+teamsId);
        return response;
    }

    /**
     * deleteTeams method deletes the team instance from the DB by retrieving the team details using the team id and deleting it using the deleteById method in the team repository
     * @param teamsId - team id is a parameter of type int which determines which team object to be retrieved to be deleted
     * @return - returns string message whether the team instance is deleted or not
     */
    public ControllerResponse deleteTeams(int teamsId)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        Optional<Teams> result = teamsRepository.findById(teamsId);

        if(result.isPresent())
        {
            teamsRepository.deleteById(teamsId);
            ControllerResponse response = new ControllerResponse("Deleted employee of id - "+ teamsId);
            return response;
        }
        else {
            throw new RuntimeException("Team id not found :"+teamsId);
        }
    }

    /**
     * getTeamResponse returns the entity with the type TeamResponse to avoid Bi-directional looping
     * @param theTeams - theTeams with type Team is passed for which the response object has to be created
     * @return - returns the response with type TeamResponse
     */
    public TeamResponse getTeamResponse(Teams theTeams)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
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
