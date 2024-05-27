package fr.uga.iut2.genevent.modele;
import fr.uga.iut2.genevent.Exception.autorisationParticipationException;
import fr.uga.iut2.genevent.util.PersonneUtilitaire;

import java.time.ZoneId;
import java.util.*;

/**
 * Classe fille de personne, type de participant à l'évènement
 */
public class Coureur extends Personne{
    private CategorieCoureur categorie;
    private int numdossard;
    private ArrayList<String> lstDocuments= new ArrayList<>();
    private double temps;
    private Course course;
    private int ageAuMomentcourse=100;
    private boolean validationInscription;
    private ArrayList<CategorieCoureur> lstCategorie=new ArrayList<>(Arrays.asList(CategorieCoureur.BABYATHLE, CategorieCoureur.EVEILATHLETIQUE, CategorieCoureur.U12_POUSINS,CategorieCoureur.U14_BENJAMINS, CategorieCoureur.U16_MINIMES,CategorieCoureur.U18_Cadets,CategorieCoureur.U20_JUNIORS,CategorieCoureur.SENIORS,CategorieCoureur.MASTERS0,CategorieCoureur.MASTERS1,CategorieCoureur.MASTERS2,CategorieCoureur.MASTERS3,CategorieCoureur.MASTERS4,CategorieCoureur.MASTERS5,CategorieCoureur.MASTERS6,CategorieCoureur.MASTERS7,CategorieCoureur.MASTERS8,CategorieCoureur.MASTERS9,CategorieCoureur.MASTERS10));
    //*#######################  CONSTRUCTEUR  #######################

    /**
     *
     * @param nom
     * @param prenom
     * @param email
     * @param sexe
     * @param dateDeNaissance
     * @param lstDocuments
     * @param course à laquelle le coureur participe
     * Id initialisé avec appel initId
     * Catégorie initialisée avec appel initCategorie
     */
    public Coureur(String nom, String prenom, String email, String sexe, Date dateDeNaissance, ArrayList<String> lstDocuments,Course course) throws autorisationParticipationException {
        super(nom,prenom,email, sexe,dateDeNaissance);
        this.lstDocuments=lstDocuments;
        initCategorie(course);
        setCourse(course);
        initId();
        setAgeAuMomentcourse();
        setNumdossard();


    }
    //*#######################  GETTERS  #######################

    public int getNumdossard() {
        return numdossard;
    }

    public ArrayList<String> getLstDocuments() {
        return lstDocuments;
    }

    public double getTemps() {
        return temps;
    }
    public CategorieCoureur getCategorie() {
        return categorie;
    }

    @Override
    public Course getCourse() {
        return course;
    }


    //*#######################  SETTERS  #######################

    public void setTemps(double temps){
        this.temps=temps;
    }
    public void setNumdossard(){
        numdossard= course.getListeCoureur().size();
    }

    /**
     * Cette fonction défini si la course du coureur si le coureur à l'autorisation de participer
     * @param course est de type Course et correspond à la course à laquelle de coureur veut s'incrire
     * @throws autorisationParticipationException
     */
    public void setCourse(Course course) throws autorisationParticipationException {
        int niveauCategorie=0;
        int niveauCategorieCourse=0;
        for(int i=0; i<lstCategorie.size();i++){
            if(course.getCategorieMinimumParticipation().equals(lstCategorie.get(i))){
                niveauCategorieCourse=i;
            }
            if (this.categorie.equals(lstCategorie.get(i))){
                niveauCategorie=i;
            }
        }
        if(niveauCategorieCourse<=niveauCategorie){
            this.course=course;
        } else {
            //eexception du coureur qui ne peut pas participer à la course parce qu'il n'est pas de la bonne catégorie
            throw new autorisationParticipationException();
        }
    }
    /**
     * Cette fonction défini l'âge qu'aura le coureur au mmoment de la course
     */
    public void setAgeAuMomentcourse(){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date datectu = Date.from(course.getEvenement().getDateDebut().atStartOfDay(defaultZoneId).toInstant());
        long elapsedms = datectu.getTime()-super.getDateDeNaissance().getTime() ;
        long diff= (long) (elapsedms/31557600000.0);
        this.ageAuMomentcourse=(int) (diff);
    }

    //*#######################  METHODES  #######################

    /**
     * Génération d'un numéro id à trois chiffres égal au dernier numéro + 1
     */
    public void initId(){
        int nbPersonne= this.course.getListeBenevole().size()+course.getListeChrono().size()+course.getListeCoureur().size();
        super.setId(PersonneUtilitaire.StringID(nbPersonne+1));
    }

    public boolean isValidationInscription() {
        return validationInscription;
    }



    /**
     * Génération d'une catégorie en fonction de l'âge du coureur, importante pour savoir si le coureur est
     * apte à participer ou non à une course
     */
    private void initCategorie(Course course){
        ZoneId defaultZoneId = ZoneId.systemDefault();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(Date.from(course.getEvenement().getDateDebut().atStartOfDay(defaultZoneId).toInstant()));
        int age= this.ageAuMomentcourse;
        if(calendar.get(GregorianCalendar.MONTH)+1>=9) {
            age++;
        }
        if(age<=6){
            categorie=CategorieCoureur.BABYATHLE;
        } else if(age>6 & age<=9){
            categorie=CategorieCoureur.EVEILATHLETIQUE;
        } else if(age>9 & age<=11){
            categorie=CategorieCoureur.U12_POUSINS;
        } else if(age>11 & age<=13){
            categorie=CategorieCoureur.U14_BENJAMINS;
        } else if(age>13 & age<=15){
            categorie=CategorieCoureur.U16_MINIMES;
        } else if(age>15 & age<=17){
            categorie=CategorieCoureur.U18_Cadets;
        } else if(age>17 & age<=19){
            categorie=CategorieCoureur.U20_JUNIORS;
        } else if(age>19 & age<=22){
            categorie=CategorieCoureur.U23_ESPOIRS;
        } else if(age>22 & age<=34){
            categorie=CategorieCoureur.SENIORS;
        } else if(age>34 & age<=39){
            categorie=CategorieCoureur.MASTERS0;
        } else if(age>39 & age<=44){
            categorie= CategorieCoureur.MASTERS1;
        } else if(age>44 & age<=49){
            categorie=CategorieCoureur.MASTERS2;
        } else if(age>49 & age<=54){
            categorie=CategorieCoureur.MASTERS3;
        } else if(age>54 & age<=59){
            categorie=CategorieCoureur.MASTERS4;
        } else if(age>59 & age<=64){
            categorie=CategorieCoureur.MASTERS5;
        } else if(age>64 & age<=69){
            categorie=CategorieCoureur.MASTERS6;
        } else if(age>69 & age<=74){
            categorie=CategorieCoureur.MASTERS7;
        } else if(age>74 & age<=79){
            categorie=CategorieCoureur.MASTERS8;
        } else if(age>79 & age<=84){
            categorie=CategorieCoureur.MASTERS9;
        } else if(age>84){
            categorie=CategorieCoureur.MASTERS10;
        }
    }

    @Override
    public String toString(){
        return super.toString()+"\n Categorie : "+ this.categorie;
    }


}
