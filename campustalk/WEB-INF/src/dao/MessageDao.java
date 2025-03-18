
package dao;

import bdd.DatabaseConnector;
import dto.base.Message;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {

    public Message findMessageById(int idMessage) {
        Message message = null;
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM message WHERE id=? AND (expiration IS NULL OR expiration > NOW())");
            stmt.setInt(1, idMessage);
            ResultSet result = stmt.executeQuery();
            if (result.next())
                message = new Message(
                        result.getInt("id"),
                        result.getTimestamp("dateheure"),
                        result.getTimestamp("expiration"),
                        result.getString("contenutexte"),
                        result.getBytes("contenuimage"),
                        result.getInt("idutilisateur"),
                        result.getInt("iddiscussion"));
            con.close();
        } catch (IOException | SQLException e) {
        }
        return message;
    }

    public List<Message> findMessageOfDiscussion(int idDiscussion) {
        ArrayList<Message> messages = new ArrayList<>();
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT id, dateheure, expiration, contenutexte, contenuimage, idutilisateur, iddiscussion FROM message WHERE iddiscussion = ? AND (expiration IS NULL OR expiration > NOW()) ORDER BY dateheure";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, idDiscussion);
            ResultSet result = stmt.executeQuery();
            con.close();

            while (result.next()) {
                Message message = new Message(
                        result.getInt("id"),
                        result.getTimestamp("dateheure"),
                        result.getTimestamp("expiration"),
                        result.getString("contenutexte"),
                        result.getBytes("contenuimage"),
                        result.getInt("idutilisateur"),
                        result.getInt("iddiscussion")
                );
                messages.add(message);
            }
        } catch (IOException | SQLException e) {
        }
        return messages;
    }

    public void sendMessage(Message message) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "INSERT INTO message (dateheure, expiration, contenutexte, contenuimage, idutilisateur, iddiscussion) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setTimestamp(1, message.getDateHeure());
            stmt.setTimestamp(2, message.getExpiration());
            stmt.setString(3, message.getContenuTexte());
            stmt.setBytes(4, message.getContenuImage()); // Stocker l'image
            stmt.setInt(5, message.getIdUtilisateur());
            stmt.setInt(6, message.getIdDiscussion());

            stmt.executeUpdate();
            con.close();
        } catch (IOException | SQLException e) {
        }
    }
}
