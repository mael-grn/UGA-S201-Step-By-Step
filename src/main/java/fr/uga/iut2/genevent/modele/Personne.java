package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.util.PersonneUtilitaire;

import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Classe mère servant à définir les attributs et méthodes communs aux classes filles : les différents usagers
 */
public class Personne implements Serializable {
    private String id;
    private String nom;
    private Course course;
    private String prenom;
    private String email;
    private Date dateDeNaissance;
    private int age;
    private String sexe;


    //*#######################  CONSTRUCTEUR  #######################

    /**
     *
     * @param nom
     * @param prenom
     * @param email
     * @param sexe
     * @param dateDeNaissance
     */
    public Personne(String nom, String prenom, String email, String sexe, Date dateDeNaissance){
        setNom(nom);
        setPrenom(prenom);
        setEmail(email);
        setSexe(sexe);
        setDateDeNaissance(dateDeNaissance);
        setAge();
    }



    public Personne(String nom, String prenom, String email, Date dateDeNaissance){
        setNom(nom);
        setPrenom(prenom);

    }


    /*######################  GETTERS #####################*/
    public String getId() {
        return id;
    }


    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
    public String getNomComplet() {
        return getPrenom() + " " + getNom();
    }
    public Course getCourse() {
        return course;
    }

    /*######################  SETTERS #####################*/

    /**
     * Cette fonction défini le nom de la personne
     * @param nom est de type String
     */
    public void setNom(String nom){
        this.nom=PersonneUtilitaire.mettreNomAuFormat(nom);
    }

    /**
     * Cette fonction défini le prenom de la personne
     * @param prenom est de type String
     */
    public void setPrenom(String prenom){
        this.prenom=PersonneUtilitaire.mettreNomAuFormat(prenom);
    }

    /**
     * Cette fonction défini la date de naissance de la personne
     * @param dateDeNaissance est de type Date
     */
    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    /**
     * Cette fonction défini l'age de la personne
     * @param age est un entier
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Cette fonction défini le sexe de la personne
     * @param sexe est de type String
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * Cette fonction défini l'email de la personne
     * @param email est de type String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Cette fonction défini l'id de la personne
     * @param id est de type String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *Cette fonction défini l'age de la personne à la date actuel
     */
    public void setAge(){
        Date datectu = new Date();
        long elapsedms = datectu.getTime()-this.dateDeNaissance.getTime() ;
        long diff= (long) (elapsedms/31557600000.0);
        this.age=(int) (diff);
    }

    /**
     * Fonction d'affichage pour une personne, retournant ses différents attributs
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : "
                + getNomComplet()
                + "\n\t Age : " + getAge()
                + "\n\t Sexe : " + getSexe()
                + "\n\t Mail : " + getEmail()
                + "\n\t Date de naissance : " + getDateDeNaissance()
                + "\n\t ID : " + getId();
    }
}
