package controleur.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DiscussionDao;
import dao.MessageDao;
import dao.ParticipationDao;
import dao.UtilisateurDao;
import dto.base.Discussion;
import dto.base.Message;
import dto.base.Participation;
import dto.base.Utilisateur;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.List;

@WebServlet("/discussions/*")
public class Discussions extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        // Authentification de l'utilisateur
        int idUtilisateur = getIdUser(req.getHeader("Authorization"));
        if (idUtilisateur == -1) {
            res.setHeader("WWW-Authenticate", "Basic realm=\"Accès restreint\"");
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentification requise");
            return;
        }

        ParticipationDao participationDao = new ParticipationDao();
        String pathInfo = req.getPathInfo();

        // Vérification des discussions
        if (pathInfo != null && !pathInfo.equals("/")) {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length != 2) {
                res.sendError(400); // Bad request
                return;
            }

            try {
                int idDiscussion = Integer.parseInt(pathParts[1]);

                // Vérifier si l'utilisateur fait partie de la discussion
                if (participationDao.findForUser(idUtilisateur, idDiscussion) == null) {
                    res.sendError(403);
                    return;
                }

                MessageDao messageDao = new MessageDao();
                List<Message> messages = messageDao.findMessageOfDiscussion(idDiscussion);

                // Si la discussion n'existe pas, retourner 404
                if (messages == null || messages.isEmpty()) {
                    res.sendError(404);
                    return;
                }

                out.println(mapper.writeValueAsString(messages));
                return;
            } catch (NumberFormatException e) {
                res.sendError(400); // Si erreur de parsing de l'id donné
                return;
            }
        }

        // Récupérer toutes les discussions de l'utilisateur
        List<Participation> participations = participationDao.findForUser(idUtilisateur);
        DiscussionDao discussionDao = new DiscussionDao();
        List<Discussion> discussions = discussionDao.findForUser(participations);

        out.println(mapper.writeValueAsString(discussions));
    }

    /*
     * Méthodes de décomposition
     */

    private int getIdUser(String authorization) {
        if (authorization == null || !authorization.startsWith("Basic")) return -1;
        String token = authorization.substring("Basic".length()).trim();
        byte[] base64 = Base64.getDecoder().decode(token);
        String[] lm = (new String(base64)).split(":");
        String mail = lm[0];
        String pwd = lm[1];


        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = utilisateurDao.find(mail, pwd);

        return (utilisateur != null) ? utilisateur.getId() : -1;
    }
}
