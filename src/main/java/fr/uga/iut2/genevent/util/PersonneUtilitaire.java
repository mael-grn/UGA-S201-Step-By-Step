package fr.uga.iut2.genevent.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonneUtilitaire {
    /**
     * Cette fonction convertit la date mit en paramètre au format String en date au format Date
     * @param s est de type String
     * @return une Date
     */
    public static Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }

    /**
     * Cette fonction défini l'id au format voulu c'est à dire avec au moins 3 caractère et si l'id entrant en a moins on complète avec des zéro à gauche de l'id
     * @param id est de type int
     * @return un String correspondant à l'id d'une personne au bon format
     */
    public static String StringID(int id){
        String stringID;
        if(id<10){
            stringID="00"+id;
        } else if(id<100){
            stringID="0"+id;
        } else {
            stringID= String.valueOf(id);
        }
        return stringID;}

    /**
     * Cette fonction renvoie le nom mit en paramètre avec la première lettre en majuscule
     * @param nom est de type String et correpsond à un nom
     * @return un string
     */
    public static String mettreNomAuFormat(String nom) {
        if (nom!= null && nom.length() > 0) {
            return nom.substring(0,1).toUpperCase()+nom.substring(1);
        } else {
            return "";
        }

    }
}
