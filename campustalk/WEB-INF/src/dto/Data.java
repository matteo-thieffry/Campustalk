package dto;

import dto.base.Discussion;
import dto.base.Utilisateur;

import java.util.*;

public class Data {
    private Utilisateur utilisateur;
    private List<Discussion> discussions;
    private List<UtilisateurParticipation> userList;
    private Discussion currentDiscussion;
    private List<MessageLikes> messageLikes;
    private boolean isAdmin;

    public Data(Utilisateur utilisateur, List<Discussion> discussions, List<UtilisateurParticipation> userList, Discussion currentDiscussion, boolean isAdmin, List<MessageLikes> messageLikes) {
        this.utilisateur = utilisateur;
        this.discussions = discussions;
        this.userList = userList;
        this.currentDiscussion = currentDiscussion;
        this.isAdmin = isAdmin;
        this.messageLikes = messageLikes;
    }

    public Data(Utilisateur utilisateur, List<Discussion> discussions, List<UtilisateurParticipation> userList) {
        this(utilisateur, discussions, userList, null, false, new ArrayList<>());
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
    }

    public List<UtilisateurParticipation> getUserList() {
        return userList;
    }

    public void setUserList(List<UtilisateurParticipation> userList) {
        this.userList = userList;
    }

    public Discussion getCurrentDiscussion() {
        return currentDiscussion;
    }

    public void setCurrentDiscussion(Discussion currentDiscussion) {
        this.currentDiscussion = currentDiscussion;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<MessageLikes> getMessageLikes() {
        return messageLikes;
    }

    public void setMessageLikes(List<MessageLikes> messageLikes) {
        this.messageLikes = messageLikes;
    }
}
