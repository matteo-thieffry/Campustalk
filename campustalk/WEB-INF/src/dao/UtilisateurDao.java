
package dao;

import bdd.DatabaseConnector;
import controleur.UserAlreadyExistsException;
import dto.base.Utilisateur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao {

    public List<Utilisateur> findAll() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT id, pseudo, mail, photo_profil FROM utilisateur";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            con.close();

            while (result.next()) {
                Utilisateur user = new Utilisateur(
                        result.getInt("id"),
                        result.getString("pseudo"),
                        result.getString("mail"),
                        result.getBytes("photo_profil"));
                utilisateurs.add(user);
            }

            return utilisateurs;
        } catch (IOException | SQLException e) {
            return utilisateurs;
        }
    }

    public Utilisateur find(String mail, String password) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection connection = dbc.createConnection();
            String loginQuery = "SELECT id, pseudo FROM utilisateur WHERE mail=? AND motdepasse=MD5(?);";
            PreparedStatement stmt = connection.prepareStatement(loginQuery);

            stmt.setString(1, mail);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();
            connection.close();

            if (result.next()) {
                return new Utilisateur(result.getInt("id"), result.getString("pseudo"), mail);
            } else {
                return null;
            }
        } catch (IOException | SQLException e) {
            return null;
        }
    }

    public Utilisateur find(String username) {
        Utilisateur utilisateur = null;
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT id, pseudo, mail FROM utilisateur WHERE pseudo=?;";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            con.close();

            if (result.next()) {
                utilisateur = new Utilisateur(result.getInt("id"), result.getString("pseudo"), result.getString("mail"));
            }
        } catch (IOException | SQLException e) {
        }
        return utilisateur;
    }

    public Utilisateur find(int id) {
        Utilisateur utilisateur = null;
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT id, pseudo, mail, photo_profil FROM utilisateur WHERE id=?;";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            con.close();

            if (result.next()) {
                utilisateur = new Utilisateur(result.getInt("id"), result.getString("pseudo"), result.getString("mail"), result.getBytes("photo_profil"));
            }
        } catch (IOException | SQLException e) {
        }
        return utilisateur;
    }

    public Utilisateur create(Utilisateur utilisateur) {
        try {
            if (checkIfUserExists(utilisateur.getMail(), utilisateur.getPseudo())) {
                throw new UserAlreadyExistsException();
            }

            DatabaseConnector dbc = new DatabaseConnector();
            Connection connection = dbc.createConnection();
            String creationQuery = "INSERT INTO utilisateur (mail, pseudo, motdepasse, photo_profil) VALUES (?, ?, MD5(?), ?);";
            PreparedStatement stmt = connection.prepareStatement(creationQuery, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, utilisateur.getMail());
            stmt.setString(2, utilisateur.getPseudo());
            stmt.setString(3, utilisateur.getPassword());
            stmt.setBytes(4, utilisateur.getPhotoProfil());

            stmt.executeUpdate();
            ResultSet result = stmt.getGeneratedKeys();
            connection.close();

            if (result.next()) utilisateur.setId(result.getInt(1));
            return utilisateur;
        } catch (IOException | SQLException | UserAlreadyExistsException e) {
            return utilisateur;
        }
    }

    private boolean checkIfUserExists(String mail, String username) throws IOException, SQLException {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection connection = dbc.createConnection();
            String query = "SELECT id FROM utilisateur WHERE mail=? OR pseudo=?;";
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, mail);
            stmt.setString(2, username);
            ResultSet result = stmt.executeQuery();
            connection.close();

            return result.next();
        } catch (IOException | SQLException e) {
            throw e;
        }
    }

    public boolean modify(int idUtilisateur, Utilisateur utilisateur) {
        String query = """
            UPDATE utilisateur 
            SET pseudo = ?, mail = ?, photo_profil = ? 
            WHERE id = ?
        """;

        try (Connection con = new DatabaseConnector().createConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, utilisateur.getPseudo());
            stmt.setString(2, utilisateur.getMail());
            stmt.setBytes(3, utilisateur.getPhotoProfil());
            stmt.setInt(4, idUtilisateur);

            stmt.executeUpdate();
            con.close();
            return true;
        } catch (IOException | SQLException e) {
            return false;
        }
    }
}
