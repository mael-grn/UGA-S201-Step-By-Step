package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
public class EvenementUtilitaire {

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
    /**
     *
     * @param coo represente la coordonée à verifier
     *
     *@return une excption est renvoyée si la coordonnée n'est pas au bon format
     */
    public static void verifierCoordonnees(String coo) throws MauvaisFormatException {
        if (obtenirFirstCoo(coo)) {
            LOGGER.log(Level.INFO, "Verification des coordonées réussie");
        } else {
            LOGGER.log(Level.WARNING, "les coordonées ne sont pas au bon format");
            throw new MauvaisFormatException("Mauvais format");
        }
    }

    /**
     * cette fonction permet de verifier si des coordonnées entré son conforme
     * elle parcours la string puis verifie si elle possède deux virgules
     * pour chaque virgule elle récupère  les coordonnées devant et verifie de leurs conformité
     * @param coordonnee une string representant des coordonnées non verifier
     * @return un boolean : true = coordonnées conforme à la norme | false = coordonnées non conforme
     */
    public static boolean obtenirFirstCoo(String coordonnee){
        String strCoLat = "0";
        String strCoLon = "0";
        boolean bCoLat = false;
        boolean bCoLon = false;
        int comptVirg = 0;
        int i = 0;
        while(i<coordonnee.length() && comptVirg <2){
            int j = 1;
            if (coordonnee.charAt(i)=='.' && comptVirg ==0){
                while(i-j>=0 && j<5 && (Character.isDigit(coordonnee.charAt(i-j)) | coordonnee.charAt(i-j) == '-')){
                    strCoLat = strCoLat+coordonnee.charAt(i-j);
                    j++;
                    bCoLat = true;
                }
                if (i-j<0){
                    j = j-1;
                }
                if (j == 5 || !(Character.isDigit(coordonnee.charAt(i-j)) | coordonnee.charAt(i-j) == '-')){
                    return false;
                }
                comptVirg++;
            } else if (coordonnee.charAt(i) == '.' && comptVirg == 1) {
                 while(i-j>=0 && j<4 && (Character.isDigit(coordonnee.charAt(i-j)) | coordonnee.charAt(i-j) == '-')){
                    strCoLon = strCoLon + coordonnee.charAt(i-j);
                    j++;
                    bCoLon = true;
                }
                if (j == 4 & (coordonnee.charAt(i-j) != ' ' & coordonnee.charAt(i-j) != '-')){
                    return false;
                }
                comptVirg++;
            }
            i++;
        }

        try {
            int coLon = Integer.parseInt(reverseClean(strCoLon));
            int coLat = Integer.parseInt(reverseClean(strCoLat));
            if (coLon>180|coLon<-180){
                return false;
            }
            if (coLat>90 | coLat<-90){
                return false;
            }
            if (bCoLon & bCoLat){
                return true;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * La fonction verifie si les coordonnées envoyé font plus de 1 charctère si oui => on supprime le premier charactère des coordonnées puis on inverse les charactères (le premier devient le dernier etc ...)
     * @param co une string représentant des coordonnées non triées
     * @return La string entrée avec la position de ses charactères inversées et son premier charactère supprimée
     */
    public static String reverseClean(String co){
        StringBuilder coBuild = new StringBuilder(co);
        if (coBuild.length()>1){
            coBuild.deleteCharAt(0);
            coBuild.reverse();
        }
        return coBuild.toString();
    }

}
