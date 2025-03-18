<%@ page import="dto.Data" %>
<%@ page import="dto.base.Discussion" %>
<%@ page import="dto.UtilisateurParticipation" %>
<%@ page import="dto.MessageLikes" %>
<!doctype html>
<html lang="fr">
<head>
    <%@ page contentType="text/html;charset=UTF-8"%>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>CampusTalk</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="./css/menu.css">
    <meta http-equiv="refresh" content="5">
</head>
<%
    Data data = (Data) session.getAttribute("userData");
    if (data == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<body>
<div class="container-fluid g-0">
    <div class="row g-0">
        <!-- Barre latérale gauche -->
        <div class="col-12 col-md-2 sidebar d-flex flex-column p-3" style="height: 100vh; position: relative;">
            <div class="header d-flex align-items-center justify-content-between px-3 py-2 w-100 flex-column flex-md-row" id="creegroup" style="position: sticky; top: 0; z-index: 1000;">
                <div class="d-flex align-items-center">
                    <img src="./images/logo.png" alt="Logo" class="logo" style="max-width: 50px; height: auto;">
                </div>
                <div>
                    <a href="groupe.jsp" class="btn btn-success d-flex align-items-center">
                        <img src="./images/imageajout.png" alt="" class="me-2" width="30">
                        <span>Créer un groupe</span>
                    </a>
                </div>
            </div>

            <!-- Liste des groupes qui défile -->
            <div class="list-group list-group-flush overflow-auto" id="listgroup" style="max-height: calc(100vh - 80px);">
                <% for (Discussion discussion: data.getDiscussions()) { %>
                <a href="home?idDisc=<%= discussion.getId() %>">
                    <div class="message d-flex align-items-center py-2" style="cursor: pointer;">
                            <button class="btn btn-light d-flex align-items-center w-100 border-0">
                                <span class="username"><%= discussion.getName() %></span>
                            </button>
                        </div>
                </a>
                <% } %>
            </div>

            <!-- Bouton de paramètres en bas -->
            <div class="header d-flex align-items-center justify-content-between px-3 py-2 w-100 flex-column flex-md-row" id="settings" style="position: sticky; bottom: 0; background: white; z-index: 1000; margin-top: auto;">
                <a href="settings.html" class="btn btn-light w-100 d-flex align-items-center justify-content-center border-0">
                    <img src="./images/settings.png" alt="Settings" style="width: 30px; height: 30px;">
                </a>
            </div>
        </div>

        <% if (data.getCurrentDiscussion() != null) {  %>
        <!-- Zone de chat -->
        <div class="col-md-8 chat-area">
            <div class="chat-header d-flex justify-content-between align-items-center">
                <div class="message d-flex align-items-center">
                    <span class="username"><%= data.getCurrentDiscussion().getName()  %></span>
                </div>
                <div class="d-flex gap-2">
                    <button class="btn btn-primary" id="addUserBtn">
                        <img src="./images/addgroupe.png" alt="Ajouter" width="20"> Ajouter
                    </button>
                    <a href="leave_groupe?idDiscussion=<%= data.getCurrentDiscussion().getId() %>" class="btn btn-danger">
                        <img src="./images/leavegroupe.png" alt="Quitter" width="20"> Quitter
                    </a>
                </div>
            </div>
            <div class="chat-messages">
                <% for (MessageLikes message: data.getMessageLikes()) {%>
                    <div class="message d-flex align-items flex-column">
                        <div class="d-flex">
                            <img src="display_image?idImage=<%= message.getAuteur().getId() %>&profil=true" class="rounded-circle me-3" style="width: 48px; height: 48px;"/>
                            <div class="message-header d-flex align-items-center justify-content message" style="height: 48px;">
                                <div class="d-flex">
                                    <span class="username"><%= message.getAuteur().getPseudo() %></span>
                                    <span class="timestamp ms-2"><%= message.getMessage().getDateHeure() %></span>
                                </div>
                                <% if (message.isLiked()) { %>
                                    <a href="like?idconv=<%= data.getCurrentDiscussion().getId() %>&idmessage=<%= message.getMessage().getId() %>" class="likes">
                                        <img src="./images/heartfill.png">
                                        <span><%= message.getNbLikes() %></span>
                                    </a>
                                <% } else { %>
                                    <a href="like?idconv=<%= data.getCurrentDiscussion().getId() %>&idmessage=<%= message.getMessage().getId() %>" class="likes">
                                        <img src="./images/heart.png">
                                        <span><%= message.getNbLikes() %></span>
                                    </a>
                                <% } %>
                            </div>
                        </div>
                        <div class="message">
                            <p class="mb-0 ">
                                <%= message.getMessage().getContenuTexte() %><br>
                                <% if (message.getMessage().getContenuImage() != null) { %>
                                    <img src="display_image?idImage=<%= message.getMessage().getId() %>&profil=false" class="imagemessage">
                                <% } %>
                            </p>
                        </div>
                    </div>
                <% } %>
            </div>

            <form action="send_message" class="input-area p-2 bg-light border rounded shadow-sm" method="post" enctype="multipart/form-data">
                <input type="hidden" name="idDiscussion" value="<%= data.getCurrentDiscussion().getId() %>">
                <div class="d-flex align-items-center gap-2" style="width: 100%;">
                    <label for="fileInput" class="btn btn-outline-secondary p-1">
                        <img src="./images/imageajout.png" alt="Ajouter une image" style="width: 24px;">
                    </label>
                    <input type="file" id="fileInput" name="image" accept="image/png" style="display: none;">
                    <input type="text" name="message" class="form-control" placeholder="Votre message">
                    <button class="btn btn-primary btn-sm" type="submit">Envoyer</button>

                    <div class="d-flex align-items-center gap-1">
                        <input type="number" id="days" class="form-control" placeholder="Jours" min="0" name="nbDays" style="width: 60px;">
                        <input type="number" id="hours" class="form-control" placeholder="Heures" min="0" max="23" name="nbHours" style="width: 60px;">
                        <input type="number" id="minutes" class="form-control" placeholder="Minutes" min="0" max="59" name="nbMinutes" style="width: 60px;">
                    </div>
                </div>
            </form>

            <!-- Modale pour ajouter un utilisateur -->
            <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
                <form action="add_user" method="post">
                    <input type="hidden" name="idDiscussion" value="<%= data.getCurrentDiscussion().getId() %>">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addUserModalLabel">Ajouter un utilisateur</h5>
                                <button type="button" name="username" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <input name="username" type="text" id="addUserInput" class="form-control" placeholder="Pseudo de l'utilisateur" readonly onfocus="this.removeAttribute('readonly');">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                <button type="submit" class="btn btn-primary" id="addUserSubmit">Ajouter</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Liste des membres -->
        <div class="col-md-2 list-member p-3" style="border-left: 2px solid #c0c0c0;">
            <h5 class="text-center">Membres</h5>
            <div class="list-group list-group-flush">
                <% for (UtilisateurParticipation utilisateur : data.getUserList()) {
                    if (utilisateur.isParticipe()) { %>
                        <div class="list-group-item d-flex align-items-center">
                            <img src="display_image?idImage=<%= utilisateur.getId() %>&profil=true" class="rounded-circle me-2" style="width: 40px; height: 40px;" alt="photo de profl">
                            <span><%= utilisateur.getPseudo() %></span>
                        </div>
                    <% } %>
                <% } %>
            </div>
        </div>
    </div>
</div>
<% } %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/modal.js"></script>
<script src="js/scroll_auto.js"></script>
</body>
</html>
