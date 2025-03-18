package dao;

import bdd.DatabaseConnector;
import dto.base.Aime;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AimeDao {

    public void like(int idUtilisateur, int idMessage) {
        String query = "INSERT INTO Aime VALUES(?,?)";
        if (alreadyLiked(idUtilisateur, idMessage)) {
            query = "DELETE FROM Aime WHERE idUtilisateur = ? AND idMessage = ?";
        }
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idMessage);
            stmt.executeUpdate();
            con.close();
        } catch (IOException | SQLException e) {}
    }

    private boolean alreadyLiked(int idUtilisateur, int idMessage) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM aime WHERE idUtilisateur=? AND idMessage=?");
            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idMessage);
            con.close();
            return stmt.executeQuery().next();
        } catch (IOException | SQLException e) {
            return false;
        }
    }

    public int countLikes(int idMessage) {
        int countLike = 0;
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM aime WHERE idmessage=?");
            stmt.setInt(1, idMessage);
            ResultSet rs = stmt.executeQuery();
            con.close();
            if (rs.next()) countLike = rs.getInt(1);
        } catch (IOException | SQLException e) {}
        return countLike;
    }

    public List<Aime> getLikes(int idUtilisateur) {
        ArrayList<Aime> likes = new ArrayList<>();
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT * FROM aime WHERE idutilisateur=?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            con.close();
            while (rs.next()) {
                likes.add(new Aime(idUtilisateur, rs.getInt("idmessage")));
            }
        } catch (IOException | SQLException e) {}
        return likes;
    }
}
