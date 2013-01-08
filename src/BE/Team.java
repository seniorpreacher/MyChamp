package BE;

public class Team {
    
    protected int ID, groupID, points;
    protected String school, teamCaptain, email, groupName;

    public Team(){
        // Dummy team.
    };
    
    public Team(int ID, String school, String teamCaptain, String email, int groupID, int points, String groupName) {
        this.ID = ID;        
        this.school = school;
        this.teamCaptain = teamCaptain;
        this.email = email;
        this.groupID = groupID;
        this.points = points;
        this.groupName = groupName;
        
    }
    
    public int getPoints() {
        return points;
    }
    public int getID() {
        return ID;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getSchool() {
        return school;
    }

    public String getTeamCaptain() {
        return teamCaptain;
    }

    public String getEmail() {
        return email;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setTeamCaptain(String teamCaptain) {
        this.teamCaptain = teamCaptain;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    
    
    /**
     * Prints out the entity in a nicer way.
     * @return a string, in the format: ID. Name of the school | Team captain: Name of the team captain.
     */
    @Override
    public String toString() {
        return getID() + ". " + getSchool() + " | Team captain: " + getTeamCaptain();
    }

}
