package fr.uga.iut2.genevent.modele;
import java.util.ArrayList;
import java.util.Date;

/**
 * classe fille de personne, organisateur relié à une liste d'évènement qu'il a créée
 */
public class Organisateur extends Personne{
    private ArrayList<Evenement> evenements;


    //*#######################  CONSTRUCTEUR  #######################

    /**
     *
     * @param nom
     * @param prenom
     * @param email
     * @param sexe
     * @param dateDeNaissance
     * @param evenements liste des evenements créé par l'organisateur
     */
    public Organisateur(String nom, String prenom, String email, String sexe, Date dateDeNaissance, ArrayList<Evenement> evenements) {
        super(nom, prenom, email, sexe, dateDeNaissance);
        setEvenements(evenements);
    }

    /**
     *
     * @param nom
     * @param prenom
     * @param email
     * @param dateDeNaissance
     * constructeur par défaut d'un organisateur
     */
    public Organisateur(String nom, String prenom, String email, Date dateDeNaissance){
        super(nom, prenom, email, dateDeNaissance);

    }

    //*#######################  GETTERS  #######################

    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }


    //*#######################  SETTERS  #######################

    /**
     * Cette fonction défini la liste d'évènement qu'organise l'organisateur
     * @param evenements est une ArrayList d'Evenement
     */

    public void setEvenements(ArrayList<Evenement> evenements) {
        this.evenements = evenements;
    }

    /**
     * Cette fonction ajoute à la liste d'évènements de l'organisateur un évènements
     * @param evenement est de type Evenement
     */
    public void addEvenement(Evenement evenement){
        if(evenements==null){
            evenements=new ArrayList<>();
            evenements.add(evenement);

        }else{
            evenements.add(evenement);

        }
    }

}
