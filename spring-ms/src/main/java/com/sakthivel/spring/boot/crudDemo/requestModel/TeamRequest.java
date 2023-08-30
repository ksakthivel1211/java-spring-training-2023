package com.sakthivel.spring.boot.crudDemo.requestModel;

import com.sakthivel.spring.boot.crudDemo.entity.Teams;

public class TeamRequest {

    private String teamName;
    private int teamCount;
    private int teamId;

    public TeamRequest(String teamName, int teamCount, int teamId) {
        this.teamName = teamName;
        this.teamCount = teamCount;
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "TeamRequest{" +
                "teamName='" + teamName + '\'' +
                ", teamCount=" + teamCount +
                ", teamId=" + teamId +
                '}';
    }

    public Teams getTeamInstance(TeamRequest teamRequest)
    {
        Teams returnTeam = new Teams(teamRequest.teamName, teamRequest.teamCount);
        return returnTeam;
    }
}
