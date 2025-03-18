package controleur;

import dao.UtilisateurDao;
import dto.base.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String mail = StringEscapeUtils.escapeHtml4(req.getParameter("mail"));
        String password = StringEscapeUtils.escapeHtml4(req.getParameter("password"))   ;

        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = utilisateurDao.find(mail, password);

        if (utilisateur != null) {
            HttpSession session = req.getSession();
            session.setAttribute("utilisateur", utilisateur);
            res.sendRedirect("home");
        } else {
            res.sendRedirect("login.html");
        }
    }

}
