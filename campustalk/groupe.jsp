<%@ page import="dto.base.Utilisateur" %>
<%@ page import="dto.Data" %>
<%@ page import="dto.UtilisateurParticipation" %>
<!doctype html>
<html lang="fr">
<head>
    <%@ page contentType="text/html;charset=UTF-8"%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="./css/styles.css">
    <title>Créer un Groupe</title>
</head>
<%
    Data data = (Data) session.getAttribute("userData");
    if (data == null) {
        response.sendRedirect("login.html");
        return;
    }
    Utilisateur currentUtilisateur = data.getUtilisateur();
%>
<body class="bg-light">
<header class="d-flex align-items-center p-3 bg-light">
    <div class="container">
        <img src="./images/logo.png" alt="Logo" width="60" height="60" class="me-2">
    </div>
</header>
<div class="container my-5">
    <h1 class="text-center mb-4">Créer un Groupe</h1>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <form action="create_discuss" method="post">
                        <input type="hidden" name="id_current_user" value="<%= currentUtilisateur.getId() %>">
                        <div class="mb-3">
                            <label class="form-label">Nom du Groupe :</label>
                            <input type="text" name="name_disc" class="form-control form-control-lg" placeholder="Nom du groupe">
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Ajouter des membres :</label>
                            <div id="emailFields">
                                <input type="text" name="pseudo_search" class="form-control form-control-lg mb-2" placeholder="Pseudo">
                            </div>

                            <div class="card mt-3">
                                <div class="list-group" style="max-height: 300px; overflow-y: auto;">
                                    <% for (UtilisateurParticipation utilisateur : data.getUserList()) { %>
                                        <% if (utilisateur.getUtilisateur().equals(currentUtilisateur)) continue; %>
                                        <div class="list-group-item d-flex align-items-center py-2">
                                            <img src="display_image?idImage=<%= utilisateur.getUtilisateur().getId() %>&profil=true" class="rounded-circle me-3" style="width: 48px; height: 48px;">
                                            <div>
                                                <div class="fw-bold"><%= utilisateur.getPseudo() %></div>
                                                <div class="text-muted"><%= utilisateur.getUtilisateur().getMail() %></div>
                                            </div>
                                            <div class="form-check ms-3">
                                                <input class="form-check-input" type="checkbox" name="user_id" value="<%= utilisateur.getId() %>" id="flexCheckDefault">
                                            </div>
                                        </div>
                                    <% } %>
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-center gap-3 mt-4">
                            <button type="button" class="btn btn-danger px-4" onclick="location.href='home'">Annuler</button>
                            <button type="submit" class="btn btn-success px-4">Valider</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/filter.js"></script>
</body>
</html>
