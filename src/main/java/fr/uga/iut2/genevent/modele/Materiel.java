package fr.uga.iut2.genevent.modele;

import java.io.Serializable;

public class Materiel implements Serializable {
    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private String nom;

    private double prixCaution = 0;
    private int quantite;
    private double prixLocation;


    //*#######################  CONSTRUCTEUR  #######################
    public Materiel(String nom, double prixLocation, int quantite, double prixCaution){
        setNom(nom);
        setPrix(prixCaution);
        setQuantite(quantite);
        setPrixLocation(prixLocation);
    }
    public Materiel(String nom, double prixLocation, int quantite){
        setNom(nom);
        setQuantite(quantite);
        setPrixLocation(prixLocation);
    }

    //*#######################  GETTERS  #######################
    public double getPrixCaution() {
        return prixCaution;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public double getPrixLocation() {
        return prixLocation;
    }
    //*#######################  SETTERS  #######################

    /**
     * Cette fonction défini le nom du materiel
     * @param nom est de type String
     */

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Cette fonction défini le prix du materiel
     * @param prix est de type String
     */
    public void setPrix(double prix) {
        prixCaution = prix;
    }

    /**
     * Cette fonction défini la quantité du materiel possédé
     * @param quantite est de type int
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Cette fonction défini leprix de location du materiel
     * @param prixLocation est de type double
     */
    public void setPrixLocation(double prixLocation){
        this.prixLocation = prixLocation;
    }
}
