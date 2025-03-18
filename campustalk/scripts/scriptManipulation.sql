-- =========================================
-- Récupérer la liste des utilisateurs
-- =========================================
SELECT U.id AS "ID", U.pseudo AS "Pseudo", U.mail AS "Email"
FROM UtilisateurSansImage AS U;

-- =========================================
-- Récupérer les messages d'une discussion donnée (ex: discussion 1)
-- =========================================
SELECT M.id AS "identifiant du message", M.dateHeure AS "date et heure", U.pseudo AS "auteur", M.contenuTexte AS "contenu textuel"
FROM MessageSansImage AS M
JOIN UtilisateurSansImage AS U ON M.idUtilisateur = U.idUtilisateur
WHERE M.idDiscussion = 1
ORDER BY M.dateHeure ASC;

-- =========================================
-- Trouver toutes les discussions auxquelles un utilisateur participe (ex: Jonathan David)
-- =========================================
SELECT D.id AS "identifiant de la discussion"
FROM Discussion AS D
JOIN Participe AS P ON D.idDiscussion = P.idDiscussion
JOIN UtilisateurSansImage AS U ON P.idUtilisateur = U.idUtilisateur
WHERE U.pseudo = 'Jonathan David';

-- =========================================
-- Vérifier si un utilisateur est administrateur d'une discussion (ex: Benjamin André)
-- =========================================
SELECT D.idDiscussion AS "identifiant de la discussion"
FROM Discussion AS D
JOIN Participe AS P ON D.idDiscussion = P.idDiscussion
JOIN UtilisateurSansImage AS U ON P.idUtilisateur = U.idUtilisateur
WHERE U.pseudo = 'Benjamin André' AND P.administrateur = TRUE;

-- =========================================
-- Récupérer les derniers messages envoyés par un utilisateur (ex: Angel Gomes)
-- =========================================
SELECT M.id AS "identifiant du message", M.dateHeure AS "date et heure", M.contenu AS "contenu texte", D.id AS "identifiant de la discussion"
FROM MessageSansImage AS M
JOIN UtilisateurSansImage AS U ON M.idUtilisateur = U.id
JOIN Discussion AS D ON M.idDiscussion = D.idDiscussion
WHERE U.pseudo = 'Angel Gomes'
ORDER BY M.dateHeure DESC;

-- =========================================
-- Trouver le nombre total de MessageSansImages envoyés par chaque UtilisateurSansImage
-- =========================================
SELECT U.pseudo AS "UtilisateurSansImage", COUNT(M.idMessageSansImage) AS "Nombre de MessageSansImages"
FROM UtilisateurSansImage AS U
LEFT JOIN MessageSansImage AS M ON U.idUtilisateurSansImage = M.idUtilisateurSansImage
GROUP BY U.pseudo
ORDER BY "Nombre de MessageSansImages" DESC;

-- =========================================
-- Trouver les MessageSansImages les plus "aimés" (likes) dans l'application
-- =========================================
SELECT M.idMessageSansImage AS "ID MessageSansImage", M.contenu AS "MessageSansImage",
       COUNT(A.idUtilisateurSansImage) AS "Nombre de Likes"
FROM MessageSansImage AS M
LEFT JOIN Aime AS A ON M.idMessageSansImage = A.idMessageSansImage
GROUP BY M.idMessageSansImage, M.contenu
ORDER BY "Nombre de Likes" DESC
LIMIT 5;

-- =========================================
-- Récupérer les UtilisateurSansImages qui ont aimé un MessageSansImage donné (ex: MessageSansImage ID 1)
-- =========================================
SELECT U.pseudo AS "UtilisateurSansImage"
FROM Aime AS A
JOIN UtilisateurSansImage AS U ON A.idUtilisateurSansImage = U.idUtilisateurSansImage
WHERE A.idMessageSansImage = 1;

-- =========================================
-- Lister les UtilisateurSansImages qui n'ont jamais envoyé de MessageSansImage
-- =========================================
SELECT U.pseudo AS "UtilisateurSansImage"
FROM UtilisateurSansImage AS U
LEFT JOIN MessageSansImage AS M ON U.idUtilisateurSansImage = M.idUtilisateurSansImage
WHERE M.idMessageSansImage IS NULL;

-- =========================================
-- Trouver le nombre de discussions actives (au moins un MessageSansImage envoyé)
-- =========================================
SELECT COUNT(DISTINCT M.idDiscussion) AS "Discussions Actives"
FROM MessageSansImage AS M;
