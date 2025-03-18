package controleur;

import dao.*;
import dto.*;
import dto.base.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/home")
public class Home extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();

        String idDiscParameter = req.getParameter("idDisc");
        if (idDiscParameter == null) {
            Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
            if (utilisateur == null) {
                res.sendRedirect("login.html");
                return;
            }
            session.setAttribute("userData", new Data(utilisateur, this.getDiscussions(utilisateur.getId()), this.getUsers(-1)));
        } else {
            int idDiscussion = Integer.parseInt(idDiscParameter);

            Data data = (Data) session.getAttribute("userData");
            if (data == null) {
                res.sendRedirect("login.html");
                return;
            }
            Utilisateur utilisateur = data.getUtilisateur();
            data.setDiscussions(this.getDiscussions(utilisateur.getId()));

            if (checkParticipation(idDiscussion, utilisateur.getId())) {
                data.setAdmin(this.checkAdmin(idDiscussion, utilisateur.getId()));
                data.setCurrentDiscussion(new DiscussionDao().find(idDiscussion, utilisateur.getId()));
                data.setMessageLikes(this.getMessageLikes(this.getMessages(idDiscussion), utilisateur.getId()));
                data.setUserList(this.getUsers(idDiscussion));
                session.setAttribute("userData", data);
            }
        }
        req.getRequestDispatcher("home.jsp").forward(req, res);
    }

    /**
     * Méthodes de décomposition
     */

    private List<Discussion> getDiscussions(int idUtilisateur) {
        ParticipationDao participationDao = new ParticipationDao();
        DiscussionDao discussionDao = new DiscussionDao();

        List<Participation> participations = participationDao.findForUser(idUtilisateur);
        List<Discussion> discussions = new ArrayList<>();
        for (Participation participation : participations) {
            discussions.add(discussionDao.find(participation.getIdDiscussion(), idUtilisateur));
        }

        return discussions;
    }

    private List<Message> getMessages(int idDiscussion) {
        MessageDao messageDao = new MessageDao();
        return messageDao.findMessageOfDiscussion(idDiscussion);
    }

    private List<UtilisateurParticipation> getUsers(int idDiscussion) {
        UtilisateurDao utilisateurDao = new UtilisateurDao();

        List<UtilisateurParticipation> users = new ArrayList<>();
        List<Utilisateur> listUsers = utilisateurDao.findAll();

        for (Utilisateur user : listUsers) {
            users.add(new UtilisateurParticipation(user, this.checkParticipation(idDiscussion, user.getId())));
        }

        return users;
    }

    private boolean checkParticipation(int idDiscussion, int idUtilisateur) {
        ParticipationDao participationDao = new ParticipationDao();
        List<Participation> participations = participationDao.findForUser(idUtilisateur);

        for (Participation participation : participations) {
            if (participation.getIdDiscussion() == idDiscussion) return true;
        }

        return false;
    }

    private boolean checkAdmin(int idDiscussion, int idUtilisateur) {
        Participation participation = new ParticipationDao().findForUser(idUtilisateur, idDiscussion);
        return participation.isAdministrateur();
    }

    private List<MessageLikes> getMessageLikes(List<Message> messages, int idUtilisateur) {
        List<MessageLikes> messagesLikes = new ArrayList<>();
        AimeDao aimeDao = new AimeDao();
        List<Aime> userLikes = aimeDao.getLikes(idUtilisateur);
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        for (Message message : messages) {
            MessageLikes messageLikes = new MessageLikes(   message,
                                                            utilisateurDao.find(message.getIdUtilisateur()),
                                                            aimeDao.countLikes(message.getId()),
                                                            checkIfLike(message.getId(), userLikes));
            messagesLikes.add(messageLikes);
        }
        return messagesLikes;
    }

    private boolean checkIfLike(int idMessage, List<Aime> userLikes) {
        for (Aime currentLike: userLikes) {
            if (idMessage == currentLike.getIdMessage()) return true;
        }
        return false;
    }
}
