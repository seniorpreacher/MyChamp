package BL;

import BE.Group;
import BE.Team;
import DAL.GroupDBManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupManager {

    private GroupDBManager DBM;
    public static void main(String[] args) throws SQLException {
        groupTeams();
    }
    public GroupManager() throws SQLException {
        DBM = new GroupDBManager();
    }

    public ArrayList<Group> getAll() throws SQLException {
        return DBM.getAll();
    }

    public void removeById(int iden) throws SQLException {
        DBM.removeById(iden);
    }

    public static void groupTeams() throws SQLException {
        TeamManager TM = new TeamManager();
        ArrayList<Team> allTeams = TM.getAll();
        int i = 1;
        int size = allTeams.size();
        for (Team team : allTeams) {
            TM.assignToGroup(team, i);
            if (i / 4 == 1) {i = 1;} else {i++;}
        }
    }
}