package UI.MenuStructure;

import BE.Team;
import BL.TeamManager;
import UI.Menu;
import UI.MenuItem;
import UI.Table;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Menu_Team extends Menu {
    
    TeamManager tm = new TeamManager();

    public Menu_Team() throws Exception {
        super("Manage");
        
        this.addItem(new MenuItem("List all", "l", new Callable<Menu_Team>(){
            @Override
            public Menu_Team call() throws Exception{
                ArrayList<Team> data = tm.getAll();
                String[][] tableData = new String[data.size()][6];
                
                
                int[] tableLayout = {4, 4, 20, 20, 20, 4};
                String[] tableHeader = {"ID", "groupID", "School", "Team Captain", "Contact E-mail", "Points"};

                for (int i = 0; i < data.size(); i++) {
                    Team team = data.get(i);
                    tableData[i][0] = Integer.toString(team.getID());
                    tableData[i][1] = Integer.toString(team.getGroupID());
                    tableData[i][2] = team.getSchool();
                    tableData[i][3] = team.getTeamCaptain();
                    tableData[i][4] = team.getEmail();
                    tableData[i][5] = Integer.toString(team.getPoints());

                }
                Table.draw(tableHeader, tableLayout, tableData);
                return new Menu_Team();
            }
        }));
        
        this.addItem(new MenuItem("Add team", "a", new Callable<Menu_Team>(){
            @Override
            public Menu_Team call() throws Exception{
                Team add = new Team();
                add.setSchool(Menu.getInput("School name"));
                add.setTeamCaptain(Menu.getInput("Team Captain's name"));
                add.setEmail(Menu.getInput("Contact E-mail"));
                return new Menu_Team();
            }
        }));
        
        this.addItem(new MenuItem("Update team", "u", new Callable<Menu_Team>(){
            @Override
            public Menu_Team call() throws Exception{
                return new Menu_Team();
            }
        }));
        
        this.addItem(new MenuItem("Remove team", "r", new Callable<Menu_Team>(){
            @Override
            public Menu_Team call() throws Exception{
                int remove = Menu.getInputInt("Team ID to remove");
                if(tm.getById(remove) != null){
                    tm.removeTeam(remove);
                    Menu.Message("Team removed from the database!");
                } else{
                    Menu.Message("Wrong ID!");
                }
                return new Menu_Team();
            }
        }));
        
        this.start();
    }
    
    @Override
    protected void addItem(MenuItem item) {
        this.items.add(item);
    }
}