package controleur;

import dao.UtilisateurDao;
import dto.base.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/register")
@MultipartConfig(maxFileSize = 1024 * 1024 * 1000)
public class Register extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String mail = StringEscapeUtils.escapeHtml4(req.getParameter("mail"));
        String pseudo = StringEscapeUtils.escapeHtml4(req.getParameter("pseudo"));
        String password = StringEscapeUtils.escapeHtml4(req.getParameter("password"));
        Part filePart = req.getPart("photo_profil");

        byte[] imageData = null;
        if (filePart != null && filePart.getSize() > 0) {
            InputStream inputStream = filePart.getInputStream();
            imageData = inputStream.readAllBytes();
        }

        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur = new Utilisateur(pseudo, mail, password, imageData);
        utilisateur = utilisateurDao.create(utilisateur);
        if (utilisateur.getId() != -1) {
            HttpSession session = req.getSession();
            session.setAttribute("utilisateur", utilisateur);
        }
        res.sendRedirect("home");
    }

}
