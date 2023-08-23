package com.sakthivel.spring.boot.crudDemo.services;

import com.sakthivel.spring.boot.crudDemo.dao.EmployeeRepository;
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
        Optional<Employee> result = employeeRepository.findById(employeeId);

        Employee theEmployee = null;
        if(result.isPresent())
        {
            theEmployee=result.get();
            return getEmployeeResponse(theEmployee);
        }
        else {
            throw new RuntimeException("Employee id not found :"+employeeId);
        }

    }

    /**
     * addEmployee method adds the employee details and employee id to the DB using the save method in employee repository
     * @param theEmployee - Employee object type passed as parameter which is added to the DB
     * @param teamId - team id of type int is passed to set the employee id for the given employee object
     * @return returns the Employee object which has been saved in the DB
     */
    public String addEmployee(Employee theEmployee,int teamId)
    {
        theEmployee.setEmployeeId(0);
        Employee dbEmployee = employeeRepository.save(theEmployee);
        Teams teams = teamsRepository.findById(teamId).orElse(null);
        teams.addEmployees(theEmployee);
        teamsRepository.save(teams);
        return "Employee details saved";
    }

    /**
     * updateEmployee method updates the existing employee details in the DB by fetching the Existing employee details using the employee id and saving the new employee object to the DB again
     * @param theEmployee - parameter of type Employee is passed as parameter which is the new employee object to be updated in the DB
     * @param employeeId - employee id is a parameter of type int which determines which employee object to be retrieved to be updated
     * @return - returns the Employee object which has been saved in the DB
     */
    public String updateEmployee(Employee theEmployee,int employeeId)
    {
        theEmployee.setEmployeeId(employeeId);
        Employee dbEmployee = employeeRepository.save(theEmployee);
        System.out.println(dbEmployee);
        return "Employee details updated on id : "+employeeId;
    }

    /**
     * deleteEmployee method deletes the employee instance from the DB by retrieving the employee details using the employee id and deleting it using the deleteById method in the employee repository
     * @param empId - employee id is a parameter of type int which determines which employee object to be retrieved to be deleted
     * @return - returns string message whether the employee instance is deleted or not
     */
    public String deleteEmployee(int empId)
    {
        Optional<Employee> result = employeeRepository.findById(empId);

        if(result.isPresent())
        {
            employeeRepository.deleteById(empId);
        }
        else {
            throw new RuntimeException("Employee id not found :"+empId);
        }
        return "Deleted employee of id - "+ empId;
    }

    public EmployeeResponse getEmployeeResponse(Employee theEmployee)
    {
        EmployeeResponse returnEmployee = new EmployeeResponse(theEmployee.getEmployeeId(),theEmployee.getFirstName(),theEmployee.getLastName(),theEmployee.getEmail());
        List<Teams> empTeam = theEmployee.getTeams();

        List<TeamResponse> teamResponses = empTeam.stream().map(theTeams -> {
            TeamResponse returnTeam = new TeamResponse(theTeams.getTeamId(),theTeams.getTeamName(),theTeams.getTeamCount());
            return returnTeam;
        }).collect(Collectors.toList());


        returnEmployee.setTeams(teamResponses);

        List<TimeSlot> empTimeSlots = theEmployee.getTimeSlots();
        List<TimeSlotResponse> timeSlotResponses = empTimeSlots.stream().map(theTimeSlot->{
            TimeSlotResponse returnTimeSlot = new TimeSlotResponse(theTimeSlot.getTimeSlotId(),theTimeSlot.getStartTime(),theTimeSlot.getEndTime(),theTimeSlot.getDate(),theTimeSlot.getBooked(),theTimeSlot.getDescription());
            return returnTimeSlot;
        }).collect(Collectors.toList());


        returnEmployee.setTimeSlots(timeSlotResponses);

        return returnEmployee;
    }

}
