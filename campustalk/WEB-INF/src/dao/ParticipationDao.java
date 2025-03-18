
package dao;

import bdd.DatabaseConnector;
import dto.base.Participation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipationDao {
    public List<Participation> findForUser(int idUtilisateur) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT * FROM participe WHERE idUtilisateur = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, idUtilisateur);
            ResultSet result = stmt.executeQuery();
            con.close();

            ArrayList<Participation> participations = new ArrayList<>();
            while (result.next()) {
                Participation participation = new Participation(result.getInt(1),
                                                                result.getInt(2),
                                                                result.getBoolean(3));
                participations.add(participation);
            }
            return participations;
        } catch (IOException | SQLException e) {
            System.out.println("Participation::findForUser::idUtilisateur " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Participation findForUser(int idUtilisateur, int idDiscussion) {
        Participation participation = null;
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT * FROM participe WHERE idUtilisateur = ? AND idDiscussion = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idDiscussion);
            ResultSet result = stmt.executeQuery();
            con.close();
            if (result.next()) participation = new Participation(result.getInt(1), result.getInt(2), result.getBoolean(3));
        } catch (IOException | SQLException e) {
            System.out.println("Participation : " + e.getMessage());
        }
        return participation;
    }

    public List<Participation> findForDiscussion(int idDiscussion) {
        List<Participation> participations = new ArrayList<>();
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT * FROM participe WHERE idDiscussion = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idDiscussion);
            ResultSet result = stmt.executeQuery();
            con.close();
            while (result.next()) {
                Participation participation = new Participation(result.getInt(1), result.getInt(2), result.getBoolean(3));
                participations.add(participation);
            }
        } catch (IOException | SQLException e) {
            System.out.println("Participation::findForUser : " + e.getMessage());
        }
        return participations;
    }

    public void create(Participation participation) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "INSERT INTO participe VALUES(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, participation.getIdUtilisateur());
            stmt.setInt(2, participation.getIdDiscussion());
            stmt.setBoolean(3, participation.isAdministrateur());
            stmt.executeUpdate();
            con.close();
        } catch (IOException | SQLException e) {
        }
    }

    public void remove(int idUtilisateur, int idDiscussion) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "DELETE FROM participe WHERE idUtilisateur = ? AND idDiscussion = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idDiscussion);
            stmt.executeUpdate();
            con.close();
        } catch (IOException | SQLException e) {
        }
    }
}
