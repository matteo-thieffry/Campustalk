package controleur;

import dao.AimeDao;
import dto.Data;
import dto.base.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/like")
public class Like extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String strIdMessage = req.getParameter("idmessage");
        String strIdConv = req.getParameter("idconv");
        int idMessage;
        try {
            idMessage = Integer.parseInt(strIdMessage);
        } catch (NumberFormatException e) {
            res.sendRedirect("home?idDisc=" + strIdConv);
            return;
        }

        HttpSession session = req.getSession();
        Data data = (Data) session.getAttribute("userData");
        Utilisateur utilisateur = data.getUtilisateur();

        AimeDao aimeDao = new AimeDao();
        aimeDao.like(utilisateur.getId(), idMessage);

        res.sendRedirect("home?idDisc=" + strIdConv);
    }
}
