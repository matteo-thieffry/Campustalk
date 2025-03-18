package controleur;

import dao.ParticipationDao;
import dto.Data;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/leave_groupe")
public class LeaveGroupe extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Data data = (Data) session.getAttribute("userData");
        if (data == null) {
            res.sendRedirect("login.html");
            return;
        }
        String strIdDiscussion = req.getParameter("idDiscussion");
        int idDiscussion = -1;
        try {
            idDiscussion = Integer.parseInt(strIdDiscussion);
        } catch (NumberFormatException e) {
            res.sendRedirect("home");
        }

        ParticipationDao participationDao = new ParticipationDao();
        participationDao.remove(data.getUtilisateur().getId(), idDiscussion);
        res.sendRedirect("home");
    }
}
