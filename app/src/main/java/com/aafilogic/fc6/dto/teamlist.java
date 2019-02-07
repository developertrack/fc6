package com.aafilogic.fc6.dto;

import java.util.ArrayList;

public class teamlist {
    String teamimage;
    String teamname;
    ArrayList<teamdetail> teamdetails;

    public teamlist(String teamimage, String teamname, ArrayList<teamdetail> teamdetails) {
        this.teamimage = teamimage;
        this.teamname = teamname;
        this.teamdetails = teamdetails;
    }

    public String getTeamimage() {
        return teamimage;
    }

    public void setTeamimage(String teamimage) {
        this.teamimage = teamimage;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public ArrayList<teamdetail> getTeamdetails() {
        return teamdetails;
    }

    public void setTeamdetails(ArrayList<teamdetail> teamdetails) {
        this.teamdetails = teamdetails;
    }
}
