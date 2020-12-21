package com.example.TRS_VIEW;



public class person {
    String nom;
    String prenom;
    String badge;


    public person(String nom, String prenom, String badge) {
        this.nom = nom;
        this.prenom = prenom;
        this.badge = badge;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    @Override
    public String toString() {

       return             ("Nom:  " + nom + "\n"

                       +"Prénom:  " + prenom + "\n"

                +"N°Badge:  " + badge +"\n");


   }
}
