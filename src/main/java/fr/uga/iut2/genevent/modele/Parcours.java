package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import fr.uga.iut2.genevent.util.EvenementUtilitaire;

import java.io.Serializable;
import java.util.ArrayList;

// Classe fini
public class Parcours implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private String coordonneesDepart;
    private ArrayList<Course> courses = new ArrayList<>();
    private String coordonneesArrivee;
    private ArrayList<String> coordonnesRavitaillement = new ArrayList<>();


    //*#######################  CONSTRUCTEUR  #######################

    public Parcours(String coordoneesDepart, String coordonnesArrivee, ArrayList<String> coordonnesRavitaillement){
        setCoordoneesDepart(coordoneesDepart);
        setCoordonnesArrivee(coordonnesArrivee);
        setCoordonnesRavitaillement(coordonnesRavitaillement);
    }


    //*##########################  GETTERS  ########################
    public String getCoordoneesDepart(){
        String temp = coordonneesDepart;
        return temp;
    }

    public String getCoordonnesArrivee() {
        String temp = coordonneesArrivee;
        return temp;
    }

    public ArrayList<String> getCoordonnesRavitaillement(){
        ArrayList<String> temp = coordonnesRavitaillement;
        return temp;
    }


    //*#######################  SETTERS  #######################

    /**
     * Le format des coordonnées entrée est vérifié
     * Type de coordonnées attendu : coordonnée décimal.
     * @param coordonneesDepart
     */
    public void setCoordoneesDepart(String coordonneesDepart) {
        try {
            EvenementUtilitaire.verifierCoordonnees(coordonneesDepart);
            this.coordonneesDepart = coordonneesDepart;
        } catch (MauvaisFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Le format des coordonnées entrée est vérifié
     * Type de coordonnées attendu : coordonnée décimal.
     * @param coordonneesArrivee
     */
    public void setCoordonnesArrivee(String coordonneesArrivee) {
        try {
            EvenementUtilitaire.verifierCoordonnees(coordonneesArrivee);
            this.coordonneesArrivee = coordonneesArrivee;
        } catch (MauvaisFormatException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Le format des coordonnées entrée est vérifié
     * Type de coordonnées attendu : coordonnées degrés décimaux.
     * @param coordonnesRavitaillement
     */
    public void setCoordonnesRavitaillement(ArrayList<String> coordonnesRavitaillement) {
            try {
                for (int i = 0; i<coordonnesRavitaillement.size(); i++){
                    EvenementUtilitaire.verifierCoordonnees(coordonnesRavitaillement.get(i));
                }
            } catch (MauvaisFormatException e) {
                throw new RuntimeException(e);
            }
        this.coordonnesRavitaillement = coordonnesRavitaillement;
    }


}
