package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Finance implements Serializable {
    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private double gain;
    private double caution = 0;
    private double depense;
    private double balance;
    private double initFinance;
    private Evenement evenement;
    private ArrayList<Transaction> historiqueGain = new ArrayList<>();
    private ArrayList<Transaction> historiquePerte = new ArrayList<>();

    //*#######################  CONSTRUCTEUR  #######################
    public Finance(double balance, Evenement evenement){
        setInitFinance(balance);
        setEvenement(evenement);
        majBalance();
    }


    //*#######################  GETTERS  #######################
    public double getBalance() {
        majBalance();
        return balance;
    }

    public double getDepense() {
        return depense;
    }

    public double getGain() {
        return gain;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public double getCaution() {
        return caution;
    }

    public ArrayList<Transaction> getHistoriqueGain() {
        return historiqueGain;
    }

    public ArrayList<Transaction> getHistoriquePerte() {
        return historiquePerte;
    }

    //*#######################  SETTERS  #######################

    /**
     * Cette fonction défini les gain de la finance
     * @param gain
     */
    public void setGain(double gain) {
        this.gain = this.gain +gain;
    }

    /**
     * Cette fonction défini la finance inital ed finance
     * @param initFinance est de type double et correpsond à la finacne inital
     */
    public void setInitFinance(double initFinance) {
        this.initFinance = initFinance;
    }

    /**
     * Cette fonction défini l'évènement de la finance
     * @param evenement est de type Evenement
     */
    public void setEvenement(Evenement evenement) {

        this.evenement = evenement;
    }

    /**
     * Cette fonction défini les dépenses de la finances
     * @param depense est de type double
     */
    public void setDepense(double depense){
        this.depense= this.depense + depense;
    }

    /**
     * Cette fonction défini la caution de la finance
     * @param caution est de type double
     */

    public void setCaution(double caution) {
        this.caution = caution;
    }
    //*#######################  METHODES  #######################

    /**
     * Cette fonction ajoute une dépense à la somme des dépenses de la finance
     * @param depense est de type depense
     */
    public void addDepense(double depense) {
        this.depense = this.depense+depense;
        majBalance();
    }

    /**
     * Cette fonction ajoute une caution à la somme des cautions de la finance
     * @param caution est de type double
     */
    public void addCaution(double caution) {
        this.caution = this.caution + caution;
    }

    /**
     * Cette fonction ajoute l'historique des gain à la finance
     * @param tr est de type transaction
     */
    public void addHistoriqueGain(Transaction tr){
        historiqueGain.add(tr);
    }

    /**
     * Cette fonction ajoute l'historique des pertes à la finance
     * @param tr est de type transaction
     */
    public void addHistoriquePerte(Transaction tr){
        historiquePerte.add(tr);
    }
    /**
     *
     * appelée pour mettre à jour la balance
     *
     * vaut la balance initiale auxquels s'ajoutent les gains et se soustraient les dépenses
     */
    private void majBalance() {
        this.balance = initFinance+gain-depense;
    }


}

