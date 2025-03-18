package controleur;

import dao.DiscussionDao;
import dao.ParticipationDao;
import dto.base.Discussion;
import dto.base.Participation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet("/create_discuss")
public class CreateDiscuss extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nameDisc = StringEscapeUtils.escapeHtml4(req.getParameter("name_disc"));
        int[] idsUsers = this.getIdsUsers(req.getParameterValues("user_id"));
        String idCurrentUserStr = req.getParameter("id_current_user");
        int idCurrentUser;
        try {
            idCurrentUser = Integer.parseInt(idCurrentUserStr);
        } catch (NumberFormatException e) {
            res.sendRedirect("home");
            return;
        }

        DiscussionDao discussionDao = new DiscussionDao();
        Discussion discussion = discussionDao.create(new Discussion(nameDisc));
        int idDiscussion = discussion.getId();

        ParticipationDao participationDao = new ParticipationDao();
        Participation admin = new Participation(idCurrentUser, idDiscussion, true);
        participationDao.create(admin);
        for (int idUser : idsUsers) {
            participationDao.create(new Participation(idUser, idDiscussion, false));
        }

        res.sendRedirect("home?idDisc=" + idDiscussion);
    }

    /**
     * Méthodes de décomposition
     */

    private int[] getIdsUsers(String[] strValues) {
        int[] ids = new int[strValues.length];
        for (int idxId = 0; idxId < strValues.length; idxId++) {
            try {
                ids[idxId] = Integer.parseInt(strValues[idxId]);
            } catch (NumberFormatException e) {}
        }
        return ids;
    }
}
