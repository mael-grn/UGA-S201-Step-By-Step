package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.PersonneUtilitaire;

import java.util.Date;

/**
 * Classe fille de personne, type de participant à l'évènement
 */
public class Chronometreur extends Personne {
    private Course course;
    //*##########################  CONSTRUCTEUR  ########################
    /**
     * @param nom
     * @param prenom
     * @param email
     * @param sexe
     * @param dateDeNaissance
     * @param course          à laquelle le chrono participe
     *                        Id initialisé avec appel initId
     */
    public Chronometreur(String nom, String prenom, String email, String sexe, Date dateDeNaissance, Course course) {
        super(nom, prenom, email, sexe, dateDeNaissance);
        this.course = course;
        initId();
    }

    //*#######################  GETTERS  #######################

    @Override
    public Course getCourse() {
        return course;
    }


    //*#######################  SETTERS  #######################

    public void setCourse(Course course) {
        this.course = course;
    }

    //*#######################  METHODES  #######################

    /**
     * Génération d'un numéro id à trois chiffres égal au dernier numéro + 1
     */
    public void initId() {
        int nbPersonne = this.course.getListeBenevole().size() + course.getListeChrono().size() + course.getListeCoureur().size();
        super.setId(PersonneUtilitaire.StringID(nbPersonne + 1));
    }




}