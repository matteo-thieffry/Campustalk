
package dao;

import bdd.DatabaseConnector;
import dto.base.Discussion;
import dto.base.Participation;
import dto.base.Utilisateur;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiscussionDao {

    public Discussion find(int idDiscussion, int idCurrentUtilisateur) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            String query = "SELECT * FROM discussion WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            
            stmt.setInt(1, idDiscussion);
            ResultSet result = stmt.executeQuery();
            con.close();

            Discussion discussion = null;
            if (result.next()) {
                String discussName = result.getString(2);
                if (discussName == null || discussName.isEmpty())
                    discussName = this.getDefaultDiscussName(result.getInt(1), idCurrentUtilisateur);
                discussion = new Discussion(result.getInt(1), discussName);
            }
            return discussion;
        } catch (IOException | SQLException e) {
            return null;
        }
    }

    public List<Discussion> findForUser(List<Participation> participations) {
        List<Discussion> discussions = new ArrayList<>();
        for (Participation participation : participations) {
            Discussion discussion = this.find(participation.getIdDiscussion(), participation.getIdUtilisateur());
            if (discussion != null) discussions.add(discussion);
        }
        return discussions;
    }

    public Discussion create(Discussion discussion) {
        try {
            DatabaseConnector dbc = new DatabaseConnector();
            Connection con = dbc.createConnection();
            // Permettre de récupérer l'id de discussion généré avec SERIAL
            PreparedStatement stmt = con.prepareStatement("INSERT INTO discussion (nom) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, discussion.getName());
            stmt.executeUpdate();

            // Récupération de l'id généré avec SERIAL
            ResultSet rs = stmt.getGeneratedKeys();
            con.close();
            if (rs.next()) discussion.setId(rs.getInt(1));
        } catch (IOException | SQLException e) {}
        return discussion;
    }

    /**
     * Méthodes de décomposition de code
     */

    private String getDefaultDiscussName(int idDiscussion, int idCurrentUtilisateur) {
        ParticipationDao participationDao = new ParticipationDao();
        List<Participation> participations = participationDao.findForDiscussion(idDiscussion);
        List<Utilisateur> utilisateurs = this.findUsers(participations);
        StringBuilder defaultDiscussName = new StringBuilder();
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getId() == idCurrentUtilisateur) continue;
            defaultDiscussName.append(utilisateur.getPseudo()).append(", ");
        }
        try {
            return defaultDiscussName.substring(0, defaultDiscussName.length() - 2);
        } catch (StringIndexOutOfBoundsException e) {
            return defaultDiscussName.toString();
        }
    }

    private List<Utilisateur> findUsers(List<Participation> participations) {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        for (Participation participation : participations) {
            utilisateurs.add(utilisateurDao.find(participation.getIdUtilisateur()));
        }
        return utilisateurs;
    }
}
