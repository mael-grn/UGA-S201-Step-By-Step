package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.validator.routines.EmailValidator;


public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    private final String email;
    private String nom;
    private String prenom;
    private final Map<String, Evenement> evenementsAdministres;  // association qualifiée par le nom
    //*##########################  CONSTRUCTEUR  ########################

    public Utilisateur(String email, String nom, String prenom) {
        assert EmailValidator.getInstance(false, false).isValid(email);
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.evenementsAdministres = new HashMap<>();
    }

    public String getEmail() {
        return this.email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    /**
     * Cette fonction défini le nom de l'utilisateur
     * @param nom est de type String
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Cette fonction défini le prenom de l'utilisateur
     * @param prenom est de type String
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Cette fonction ajoute un evenement administrer à l'utilisateur
     * @param evt est de type Evenement
     */
    public void ajouteEvenementAdministre(Evenement evt) {
        assert !this.evenementsAdministres.containsKey(evt.getNom());
        this.evenementsAdministres.put(evt.getNom(), evt);
    }
}
