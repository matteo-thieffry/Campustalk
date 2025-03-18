
-- =========================================
-- Suppression des tables
-- =========================================

DROP TABLE IF EXISTS Aime, Participe, Message, Discussion, Utilisateur CASCADE;
DROP VIEW MessageSansImage, UtilisateurSansImage;

-- =========================================
-- Table UTILISATEUR
-- =========================================
CREATE TABLE Utilisateur (
    id SERIAL,
    pseudo VARCHAR(40) NOT NULL,
    mail TEXT NOT NULL,
    motDePasse TEXT NOT NULL,
    photo_profil BYTEA,
    CONSTRAINT pk_utilisateur PRIMARY KEY (id)
);  

CREATE VIEW UtilisateurSansImage AS
    SELECT id, pseudo, mail, motDePasse FROM Utilisateur;

-- =========================================
-- Table DISCUSSION
-- =========================================
CREATE TABLE Discussion (
    id SERIAL,
    nom VARCHAR(30),
    CONSTRAINT pk_discussion PRIMARY KEY (id)
);

-- =========================================
-- Table MESSAGE
-- =========================================
CREATE TABLE Message (
    id SERIAL,
    dateHeure TIMESTAMP NOT NULL,
    expiration TIMESTAMP,
    contenuTexte TEXT NOT NULL,
    contenuImage BYTEA,
    idUtilisateur INT NOT NULL,
    idDiscussion INT NOT NULL,
    CONSTRAINT pk_message PRIMARY KEY (id),
    CONSTRAINT fk_message_utilisateur FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_discussion FOREIGN KEY (idDiscussion) REFERENCES Discussion(id) ON DELETE CASCADE
);

CREATE VIEW MessageSansImage AS
    SELECT id, dateHeure, expiration, contenuTexte, idUtilisateur, idDiscussion FROM Message;

-- =========================================
-- Table PARTICIPE (relation entre Utilisateur et Discussion avec rôle administrateur)
-- =========================================
CREATE TABLE Participe (
    idUtilisateur INT NOT NULL,
    idDiscussion INT NOT NULL,
    administrateur BOOLEAN NOT NULL,
    CONSTRAINT pk_participe PRIMARY KEY (idUtilisateur, idDiscussion),
    CONSTRAINT fk_participe_utilisateur FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    CONSTRAINT fk_participe_discussion FOREIGN KEY (idDiscussion) REFERENCES Discussion(id) ON DELETE CASCADE
);

-- =========================================
-- Table AIME (relation entre Utilisateur et Message)
-- =========================================
CREATE TABLE Aime (
    idUtilisateur INT NOT NULL,
    idMessage INT NOT NULL,
    CONSTRAINT pk_aime PRIMARY KEY (idUtilisateur, idMessage),
    CONSTRAINT fk_aime_utilisateur FOREIGN KEY (idUtilisateur) REFERENCES Utilisateur(id) ON DELETE CASCADE,
    CONSTRAINT fk_aime_message FOREIGN KEY (idMessage) REFERENCES Message(id) ON DELETE CASCADE
);
