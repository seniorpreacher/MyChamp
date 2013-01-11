package UI.MenuStructure;

import BE.Team;
import BL.TeamManager;
import UI.Menu;
import UI.MenuItem;
import UI.Table;
import UI.Table_project;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Menu_Team extends Menu {

    TeamManager tm = new TeamManager();

    public Menu_Team() throws Exception {
        super("Manage");

        this.addItem(new MenuItem("List all", "l", new Callable<Menu_Team>() {
            @Override
            public Menu_Team call() throws Exception {
                Table_project.fromTeams(tm.getAllWithGroupNames());
                return new Menu_Team();
            }
        }));

        this.addItem(new MenuItem("Add team", "a", new Callable<Menu_Team>() {
            @Override
            public Menu_Team call() throws Exception {
                Team add = new Team();
                add.setSchool(Menu.getInput("School name"));
                add.setTeamCaptain(Menu.getInput("Team Captain's name"));
                add.setEmail(Menu.getInput("Contact E-mail"));
                tm.addTeam(add);
                return new Menu_Team();
            }
        }));

        this.addItem(new MenuItem("Update team", "u", new Callable<Menu_Team>() {
            @Override
            public Menu_Team call() throws Exception {
                Team update = null;
                try {
                    int in = Menu.getInputInt("Team ID to update");
                    if (in > 0) {
                        update = tm.getById(in);
                    }
                } catch (SQLException e) {
                    Menu.Message("Wrong ID!");
                    return new Menu_Team();
                }

                if (update == null) {
                    Menu.Message("Wrong ID!");
                    return new Menu_Team();
                }

                Menu.Message("What data would you like to change?");
                Menu.Message("0: Back");
                Menu.Message("1: School name");
                Menu.Message("2: Team Captain");
                Menu.Message("3: E-mail address");
                int submenu;
                do {
                    submenu = Menu.getInputInt("Please choose from above");
                } while (!(submenu > -1 && submenu < 4));

                if (submenu == 0) {
                    return new Menu_Team();
                }

                String newValue = Menu.getInput("New value");
                switch (submenu) {
                    case 1:
                        update.setSchool(newValue);
                        break;
                    case 2:
                        update.setTeamCaptain(newValue);
                        break;
                    case 3:
                        update.setEmail(newValue);
                        break;
                }
                tm.updateTeam(update);
                return new Menu_Team();
            }
        }));

        this.addItem(new MenuItem("Remove team", "r", new Callable<Menu_Team>() {
            @Override
            public Menu_Team call() throws Exception {
                int remove = Menu.getInputInt("Team ID to remove");
                try {
                    tm.getById(remove);
                } catch (SQLException e) {
                    Menu.Message("Wrong ID!");
                    return new Menu_Team();
                }
                if (remove > 0) {
                    tm.removeTeam(remove);
                    Menu.Message("Team removed from the database!");
                }

                return new Menu_Team();
            }
        }));

        this.start();
    }
}
