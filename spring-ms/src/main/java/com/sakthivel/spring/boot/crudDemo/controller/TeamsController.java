package com.sakthivel.spring.boot.crudDemo.controller;

import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.requestModel.TeamRequest;
import com.sakthivel.spring.boot.crudDemo.resposeModel.ControllerResponse;
import com.sakthivel.spring.boot.crudDemo.resposeModel.TeamResponse;
import com.sakthivel.spring.boot.crudDemo.services.TeamService;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class TeamsController {

    private TeamService teamService;

    private TeamRequest teamRequest;

    @Autowired
    public TeamsController(TeamService theTeamService,TeamRequest theTeamRequest){
        teamService=theTeamService;
        teamRequest=theTeamRequest;
    }

    /**
     * findAllTeams method gives details of all teams
     * @return - returns List of Teams objects
     */
    @GetMapping("/teams")
    public ResponseEntity<List<TeamResponse>> findAllTeams()
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getAllTeams());
    }

    /**
     * getTeam method gives the details of team whose id is given
     * @param teamId - teamId of type int passed as parameter
     * @return - return team object whose id is given
     */
    @GetMapping("/teams/{teamsId}")
    public ResponseEntity<TeamResponse> getTeam(@PathVariable int teamId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getTeamById(teamId));
    }

    /**
     * addTeam methods saves the Team object to the DB
     * @param theTeams - employee object from request body is passed as parameter
     * @return - returns responseEntity of type controllerResponse for the confirmation of saving the object
     */
    @PostMapping("/teams")
    public ResponseEntity<ControllerResponse> addTeam(@RequestBody Teams theTeams)
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.addTeams(theTeams));
    }

    /**
     * updateTeam method updates the Team object to the DB
     * @param theTeams - team object from request body is passed as parameter
     * @param teamsId - team id of type int passed as parameter
     * @return returns response object of type ControllerResponse
     */
    @PutMapping("/teams/{teamsId}")
    public ResponseEntity<ControllerResponse> updateTeam(@RequestBody TeamRequest theTeamRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.updateTeams(teamRequest.getTeamInstance(theTeamRequest),theTeamRequest.getTeamId());
    }

    /**
     * deleteEmployee method deletes the employee
     * @param teamsId - employeeId of type int passed as parameter
     * @return returns response object of type ControllerResponse
     */
    @DeleteMapping("/teams/{teamsId}")
    public ResponseEntity<ControllerResponse> deleteTeam(@PathVariable int teamsId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.deleteTeams(teamsId));
    }

}