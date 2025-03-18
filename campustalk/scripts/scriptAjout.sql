-- =========================================
-- INSÉRER DES UTILISATEURS
-- =========================================
INSERT INTO Utilisateur (pseudo, mail, motDePasse) VALUES
    ('Jonathan David', 'jonathan.david@losc.fr', MD5('password123')),
    ('Rémy Cabella', 'remy.cabella@losc.fr', MD5('securepass')),
    ('Benjamin André', 'benjamin.andre@losc.fr', MD5('12345678')),
    ('Angel Gomes', 'angel.gomes@losc.fr', MD5('letmein')),
    ('Bafodé Diakité', 'bafode.diakite@losc.fr', MD5('mypassword'));

-- =========================================
-- INSÉRER DES DISCUSSIONS
-- =========================================
INSERT INTO Discussion (nom) VALUES ('Groupe 1');
INSERT INTO Discussion DEFAULT VALUES;
INSERT INTO Discussion DEFAULT VALUES;

-- =========================================
-- INSÉRER DES PARTICIPATIONS
-- =========================================
INSERT INTO Participe (idUtilisateur, idDiscussion, administrateur) VALUES
    (1, 1, TRUE),
    (2, 1, FALSE),
    (3, 1, FALSE),

    (3, 2, TRUE),
    (4, 2, FALSE),

    (5, 3, TRUE),
    (1, 3, FALSE);

-- =========================================
-- INSÉRER DES MESSAGES
-- =========================================
INSERT INTO Message (dateHeure, contenuTexte, idUtilisateur, idDiscussion) VALUES
    ('2025-02-10 10:15:00', 'Salut les gars, prêt pour le match de ce week-end ?', 1, 1),
    ('2025-02-10 10:20:00', 'Oui, je suis prêt ! On va tout donner.', 2, 1),
    ('2025-02-11 09:00:00', 'N''oubliez pas l''entraînement demain matin.', 3, 2),
    ('2025-02-11 09:05:00', 'Merci pour le rappel, à demain.', 4, 2),
    ('2025-02-12 14:30:00', 'Bonne séance aujourd''hui, l''équipe est en forme.', 5, 3),
    ('2025-02-12 14:35:00', 'Oui, continuons comme ça pour le prochain match.', 1, 3);

-- =========================================
-- INSÉRER DES LIKES
-- =========================================
INSERT INTO Aime (idUtilisateur, idMessage) VALUES
    (2, 1),
    (1, 2),
    (4, 3),
    (3, 4),
    (1, 5),
    (5, 6);
