package DAL;

import BE.Group;
import BE.Match;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MatchDBManager extends DBManager {

    public MatchDBManager() throws SQLException {
        super();
    }

    public void addMatch(Match match) throws SQLException {
        Connection con = dS.getConnection();
        PreparedStatement qMatch = con.prepareStatement("INSERT INTO Match VALUES (?, ?, ?, ?, ?, ?)");


        qMatch.setInt(1, match.getRound());
        qMatch.setInt(2, match.getHomeTeamID());
        qMatch.setInt(3, match.getGuestTeamID());
        qMatch.setInt(4, match.getIsPlayed());
        qMatch.setNull(5, java.sql.Types.INTEGER);
        qMatch.setNull(6, java.sql.Types.INTEGER);

        qMatch.executeUpdate();

        con.close();
    }
    /*
     public void updateMatch(Match match) throws SQLException {
     Connection con = dS.getConnection();

     PreparedStatement qTeam = con.prepareStatement("UPDATE Match SET  Round = ?, HomeTeamID = ?, GuestTeamID = ?, isPlayed = ? WHERE ID = ?");

     qTeam.setInt(1, match.getRound());
     qTeam.setInt(2, match.getHomeTeamID());
     qTeam.setInt(3, match.getGuestTeamID());
     qTeam.setInt(4, match.isIsPlayed());

     qTeam.setInt(5, match.getID());

     qTeam.executeUpdate();

     con.close();
     }*/

    public void updateScore(Match match) throws SQLException {
        Connection con = dS.getConnection();

        PreparedStatement qTeam = con.prepareStatement("UPDATE Match SET  HomeGoals = ?, GuestGoals = ? WHERE ID = ?");


        qTeam.setInt(1, match.getHomeGoals());
        qTeam.setInt(2, match.getGuestGoals());

        qTeam.setInt(3, match.getID());

        qTeam.executeUpdate();

        con.close();
    }

    @Override
    public void removeById(int iden) throws SQLException {
        Connection con = dS.getConnection();
        PreparedStatement qMatch = con.prepareStatement("DELETE FROM Match WHERE ID = ?");
        qMatch.setInt(1, iden);

        qMatch.executeUpdate();

        con.close();
    }

    public Match getMatchById(int id) throws SQLException {
        Connection con = dS.getConnection();
        Match match;

        PreparedStatement qAllMatches = con.prepareStatement("SELECT * FROM Match WHERE Match.ID = ?;");
        qAllMatches.setInt(1, id);
        ResultSet allMatches = qAllMatches.executeQuery();

        allMatches.next();
        match = new Match(
                allMatches.getInt("ID"),
                allMatches.getInt("MatchRound"),
                allMatches.getInt("HomeTeamID"),
                allMatches.getInt("GuestTeamID"),
                allMatches.getInt("IsPlayed"),
                allMatches.getInt("HomeGoals"),
                allMatches.getInt("GuestGoals"));

        con.close();
        return match;

    }

    @Override
    public ArrayList<Match> getAll() throws SQLException {
        Connection con = dS.getConnection();
        ArrayList<Match> matches = new ArrayList<>();

        PreparedStatement qAllMatches = con.prepareStatement("SELECT Match.ID, Match.MatchRound, Match.HomeTeamID, Match.GuestTeamID, Match.IsPlayed, Match.HomeGoals, Match.GuestGoals, t1.School as HomeTeamName, t2.School as GuestTeamName FROM Match INNER JOIN Team as t1 ON t1.ID = Match.HomeTeamID INNER JOIN Team as t2 ON t2.ID = Match.GuestTeamID ORDER BY Match.MatchRound ASC;");
        ResultSet allMatches = qAllMatches.executeQuery();

        while (allMatches.next()) {
            matches.add(
                    new Match(
                    allMatches.getInt("ID"),
                    allMatches.getInt("MatchRound"),
                    allMatches.getInt("HomeTeamID"),
                    allMatches.getInt("GuestTeamID"),
                    allMatches.getInt("IsPlayed"),
                    allMatches.getInt("HomeGoals"),
                    allMatches.getInt("GuestGoals"),
                    allMatches.getString("HomeTeamName"),
                    allMatches.getString("GuestTeamName")));
        }

        con.close();
        return matches;

    }

    public ArrayList<Match> getMatchesByGroup(Group g) throws SQLException {
        Connection con = dS.getConnection();
        ArrayList<Match> matches = new ArrayList<>();

        PreparedStatement qAllMatches = con.prepareStatement("SELECT Match.* FROM Match INNER JOIN Team ON Match.HomeTeamID = Team.ID WHERE Team.GroupID = ?");
        qAllMatches.setInt(1, g.getID());
        ResultSet allMatches = qAllMatches.executeQuery();

        while (allMatches.next()) {
            matches.add(
                    new Match(
                    allMatches.getInt("ID"),
                    allMatches.getInt("MatchRound"),
                    allMatches.getInt("HomeTeamID"),
                    allMatches.getInt("GuestTeamID"),
                    allMatches.getInt("IsPlayed"),
                    allMatches.getInt("HomeGoals"),
                    allMatches.getInt("GuestGoals")));
        }

        con.close();
        return matches;

    }

    @Override
    public void removeAll() throws SQLException {
        Connection con = dS.getConnection();
        PreparedStatement qData = con.prepareStatement("DELETE FROM Match; DBCC CHECKIDENT (Match, RESEED, 0)");
        qData.executeUpdate();

        con.close();
    }
}
