package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import fr.uga.iut2.genevent.util.EvenementUtilitaire;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class Parking implements Serializable {
    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private static Logger LOGGER = Logger.getLogger(EvenementUtilitaire.class.getPackageName());
    private static final LogManager logManager = LogManager.getLogManager();
    static {
        try {
            logManager.readConfiguration(new FileInputStream("conf/debuglogging.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Cannot read configuration file",
                    exception);
        }
    }
    private String libelle;
    private int nbPlaces;
    //les coordonées GPS sont au format de degrés decimaux
    private String coordones;

    //*#######################  CONSTRUCTEUR  #######################


    public Parking(String libelle, int nbPlaces, String coordones){
        this.libelle = libelle;
        this.nbPlaces = nbPlaces;
        this.coordones = coordones;


/*
        try {
            EvenementUtilitaire.verifierCoordonnees(coordones);
        } catch (MauvaisFormatException e) {
            //coder une fonction dans les classes appelantes permettant de faire corriger par l'utilisateur le format
            throw new RuntimeException(e);
        }

 */
    }

    //*#######################  GETTERS  #######################

    /**
     * @return une copie du nombre place, donc non modifiable
     */
    public int getNbPlaces() {
        int temp = nbPlaces;
        return temp;
    }
    /**
     * @return une copie des coordonées, donc non modifiable
     */
    public String getCoordones() {
        String temp = coordones;
        return temp;
    }
    public String getLibelle() {
        return libelle;
    }

    //*#######################  SETTERS  #######################


    public void setCoordones(String coordones) throws RuntimeException{
        try {
            EvenementUtilitaire.verifierCoordonnees(coordones);
            this.coordones = coordones;
        } catch (MauvaisFormatException e) {
            LOGGER.log(Level.WARNING, "coordonées non mises à jour car le format de correspond pas");
            throw new RuntimeException(e);
        }
    }

    /**
     * Cette fonction défini le nombre de place qu'il y a dans ce parking
     * @param nbPlaces est un entier
     */
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }
}
