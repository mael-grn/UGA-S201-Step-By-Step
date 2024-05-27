package fr.uga.iut2.genevent.modele;

import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private double montant;
    private String libelle;
    private String date;
    //*##########################  CONSTRUCTEUR  ########################
    public Transaction(String libelle, double montant, String date){
        setDate(date);
        setLibelle(libelle);
        setMontant(montant);
    }
    //#######################  GETTERS  ######################

    public double getMontant() {
        return montant;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getDate() {
        return date;
    }

    //#######################  SETTERS  #######################

    /**
     * Cette fonction défini la date de la transaction
     * @param date est de type String
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Cette fonction défini le libelle le libelle de la trnsaction
     * @param libelle est de type String
     */
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    /**
     * Cette fonction défini le montant de la transaction
     * @param montant est de type double
     */
    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return ""+montant;
    }
}
