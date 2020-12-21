package com.example.TRS_VIEW;

public class person2 {
    private String of;
    private String produit;
    private String matiere;
    private String qte_fab;
    private String cadence ;
    private String pres;

    public person2( String produit, String of, String matiere,String pres,String qte_fab,String cadence) {
        this.of = of;
        this.produit = produit;
        this.matiere = matiere;
        this.pres = pres;
        this.qte_fab = qte_fab;
        this.cadence = cadence;


    }

    public String getOf() {
        return of;
    }

    public void setOf(String of) {
        this.of = of;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getQte_fab() {
        return qte_fab;
    }

    public void setQte_fab(String qte_fab) {
        this.qte_fab = qte_fab;
    }

    public String getCadence() {
        return cadence;
    }

    public void setCadence(String cadence) {
        this.cadence = cadence;
    }


    @Override
    public String toString() {

        return ( produit+"°" + of+"°" + matiere + "°" + pres + "°" +qte_fab+"°" +cadence+"°");

    }
}