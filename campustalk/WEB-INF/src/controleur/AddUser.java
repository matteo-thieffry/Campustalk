package controleur;

import dao.ParticipationDao;
import dao.UtilisateurDao;
import dto.base.Participation;
import dto.base.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet("/add_user")
public class AddUser extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String username = StringEscapeUtils.escapeHtml4(req.getParameter("username"));
        String strIdDiscussion = req.getParameter("idDiscussion");
        int idDiscussion;
        try {
            idDiscussion = Integer.parseInt(strIdDiscussion);
        } catch (NumberFormatException e) {
            res.sendRedirect("home?idDisc="+strIdDiscussion);
            return;
        }

        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = utilisateurDao.find(username);
        if (utilisateur == null) {
            res.sendRedirect("home?idDisc="+strIdDiscussion);
            return;
        }

        Participation participation = new Participation(utilisateur.getId(), idDiscussion, false);
        ParticipationDao participationDao = new ParticipationDao();
        participationDao.create(participation);

        res.sendRedirect("home?idDisc="+strIdDiscussion);
    }
}
