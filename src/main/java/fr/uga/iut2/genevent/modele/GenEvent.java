package fr.uga.iut2.genevent.modele;

import java.io.Serializable;
import java.util.ArrayList;


public class GenEvent implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private ArrayList<Organisateur> organisateurs;

    //*##########################  CONSTRUCTEUR  ########################
    public GenEvent(ArrayList<Organisateur> organisateurs) {
        this.organisateurs = organisateurs;
    }

    public ArrayList<Organisateur> getOrganisateurs() {
        return organisateurs;
    }

    /**
     * Cette fonction ajoute un organisateur
     * @param orga est de type Organisateur
     */

    public void addOrganisateur(Organisateur orga) {
        if (organisateurs == null) {
            organisateurs = new ArrayList<>();
        }
        if (orga != null) {
            organisateurs.add(orga);
        }
    }
}
