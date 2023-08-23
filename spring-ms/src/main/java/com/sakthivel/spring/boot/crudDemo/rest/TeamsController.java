package com.sakthivel.spring.boot.crudDemo.rest;

import com.sakthivel.spring.boot.crudDemo.dao.TeamsRepository;
import com.sakthivel.spring.boot.crudDemo.entity.Teams;
import com.sakthivel.spring.boot.crudDemo.respose.TeamResponse;
import com.sakthivel.spring.boot.crudDemo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class TeamsController {

    private TeamService teamService;

    @Autowired
    public TeamsController(TeamService theTeamService){
        teamService=theTeamService;
    }

    @GetMapping("/teams")
    public List<TeamResponse> findAllTeams()
    {
        return teamService.getAllTeams();
    }

    @GetMapping("/teams/{teamsId}")
    public TeamResponse getTeam(@PathVariable int teamsId)
    {
        return teamService.getTeamById(teamsId);
    }

    @PostMapping("/teams")
    public String addTeam(@RequestBody Teams theTeams)
    {
        return teamService.addTeams(theTeams);
    }

    @PutMapping("/teams/{teamsId}")
    public String updateTeam(@RequestBody Teams theTeams, @PathVariable int teamsId)
    {
        return teamService.updateTeams(theTeams,teamsId);
    }

    @DeleteMapping("/teams/{teamsId}")
    public String deleteTeam(@PathVariable int teamsId)
    {
        return teamService.deleteTeams(teamsId);
    }

}