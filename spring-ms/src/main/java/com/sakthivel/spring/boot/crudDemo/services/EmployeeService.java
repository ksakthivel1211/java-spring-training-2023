package com.sakthivel.spring.boot.crudDemo.services;

import com.sakthivel.spring.boot.crudDemo.customException.BookingException;
import com.sakthivel.spring.boot.crudDemo.dao.EmployeeRepository;
import com.sakthivel.spring.boot.crudDemo.dao.TeamsRepository;
import com.sakthivel.spring.boot.crudDemo.entity.Employee;
import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.entity.TimeSlot;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.EmployeeResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TeamResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TimeSlotResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private TeamsRepository teamsRepository;

    public EmployeeService(){}

    @Autowired
    public EmployeeService(EmployeeRepository theEmployeeRepository,TeamsRepository theTeamRepository)
    {
        employeeRepository=theEmployeeRepository;
        teamsRepository=theTeamRepository;
    }

    /**
     * getAllEmployees method returns all the existing employees
     * @return - returns List of all employees object
     */
    public List<EmployeeResponse> getAllEmployees()
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        List<Employee> theEmployees= employeeRepository.findAll();
        List<EmployeeResponse> employeeResponses = theEmployees.stream().map(theEmployee->{
            return getEmployeeResponse(theEmployee);
        }).collect(Collectors.toList());

        return employeeResponses;
    }

    /**
     * getEmployeeById method returns employee details (Employee object) whose id is given
     * @param employeeId - employee id with type int passed as parameter whose employee details should be found
     * @return returns the employee Object of the given Employee id
     */
    public EmployeeResponse getEmployeeById(int employeeId)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        Optional<Employee> result = employeeRepository.findById(employeeId);

        Employee theEmployee = null;
        if(result.isPresent())
        {
            theEmployee=result.get();
            return getEmployeeResponse(theEmployee);
        }
        else {
            throw new BookingException("Employee id not found :"+employeeId);
        }

    }

    /**
     * addEmployee method adds the employee details and employee id to the DB using the save method in employee repository
     * @param theEmployee - Employee object type passed as parameter which is added to the DB
     * @param teamId - team id of type int is passed to set the employee id for the given employee object
     * @return returns the Employee object which has been saved in the DB
     */
    public ControllerResponse addEmployee(Employee theEmployee, int teamId)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        theEmployee.setEmployeeId(0);
        Employee dbEmployee = employeeRepository.save(theEmployee);
        Teams teams = teamsRepository.findById(teamId).orElse(null);
        teams.addEmployees(theEmployee);
        teamsRepository.save(teams);
        ControllerResponse response = new ControllerResponse("Employee details saved");
        return response;
    }

    /**
     * updateEmployee method updates the existing employee details in the DB by fetching the Existing employee details using the employee id and saving the new employee object to the DB again
     * @param theEmployee - parameter of type Employee is passed as parameter which is the new employee object to be updated in the DB
     * @param employeeId - employee id is a parameter of type int which determines which employee object to be retrieved to be updated
     * @return - returns the Employee object which has been saved in the DB
     */
    public ControllerResponse updateEmployee(Employee theEmployee,int employeeId)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        theEmployee.setEmployeeId(employeeId);
        Employee dbEmployee = employeeRepository.save(theEmployee);
        System.out.println(dbEmployee);
        ControllerResponse response = new ControllerResponse("Employee details updated on id : "+employeeId);
        return response;
    }

    /**
     * deleteEmployee method deletes the employee instance from the DB by retrieving the employee details using the employee id and deleting it using the deleteById method in the employee repository
     * @param empId - employee id is a parameter of type int which determines which employee object to be retrieved to be deleted
     * @return - returns string message whether the employee instance is deleted or not
     */
    public ControllerResponse deleteEmployee(int empId)
    {
        try {

        }
        catch (Exception exception)
        {
            throw new BookingException(exception.getMessage());
        }
        Optional<Employee> result = employeeRepository.findById(empId);

        if(result.isPresent())
        {
            employeeRepository.deleteById(empId);
            ControllerResponse response = new ControllerResponse("Deleted employee of id - "+ empId);
            return response;
        }
        else {
            throw new BookingException("Employee id not found :"+empId);
        }

    }

    /**
     * getEmployeeResponse returns the entity with the type EmployeeResponse to avoid Bi-directional looping
     * @param theEmployee - theEmployee with type Employee is passed for which the response object has to be created
     * @return - returns the response with type EmployeeResponse
     */
    public EmployeeResponse getEmployeeResponse(Employee theEmployee) {
        try {
            EmployeeResponse returnEmployee = new EmployeeResponse(theEmployee.getEmployeeId(), theEmployee.getFirstName(), theEmployee.getLastName(), theEmployee.getEmail());
            List<Teams> empTeam = theEmployee.getTeams();
            List<TeamResponse> teamResponses = empTeam.stream()
                    .map(theTeams -> new TeamResponse(theTeams.getTeamId(), theTeams.getTeamName(), theTeams.getTeamCount()))
                    .collect(Collectors.toList());
            List<TimeSlot> empTimeSlots = theEmployee.getTimeSlots();
            List<TimeSlotResponse> timeSlotResponses = empTimeSlots.stream().map(theTimeSlot -> new TimeSlotResponse(theTimeSlot.getTimeSlotId(), theTimeSlot.getStartTime(), theTimeSlot.getEndTime(), theTimeSlot.getDate(), theTimeSlot.getBooked(), theTimeSlot.getDescription())).collect(Collectors.toList());

            returnEmployee.setTeams(teamResponses);
            returnEmployee.setTimeSlots(timeSlotResponses);

            return returnEmployee;
        } catch (Exception e) {
            throw new BookingException(e.getMessage());
        }
    }

}
