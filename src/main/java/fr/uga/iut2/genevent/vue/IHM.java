package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.modele.*;

import java.util.ArrayList;
import java.util.Date;


public abstract class IHM {
    /**
     * Classe conteneur pour les informations saisies à propos d'un
     * {@link fr.uga.iut2.genevent.modele.Utilisateur}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */


    /**
     * Rend actif l'interface Humain-machine.
     *
     * L'appel est bloquant : le contrôle est rendu à l'appelant une fois que
     * l'IHM est fermée.
     *
     */
    public abstract void demarrerInteraction();

    /**
     * Affiche un message d'information à l'attention de l'utilisa·teur/trice.
     *
     * @param msg Le message à afficher.
     *
     * @param succes true si le message informe d'une opération réussie, false
     *     sinon.
     */
    public abstract void informerUtilisateur(final String msg, final boolean succes);

    public abstract void afficherListeOrganisateurVbox(ArrayList<Organisateur> content);


    public abstract void saisirNouvelOrganisateur();

    public abstract void afficherPageVosEvenements(ArrayList<Evenement> events);

    public abstract void afficherInfosPagePrincipale(String nomEvent, Date dateDeb, Date dateFin, int budget, int solde, int nbrCoureur, int nbrBenev);

    public abstract void afficherInfosPageCourses(ArrayList<Course> courses, String nomEvent);

    public abstract void afficherInfosPageCoureurs(ArrayList<Coureur> coureurs, String nomEvent);

    public abstract void afficherInfosPageBenevs(ArrayList<Benevole> benevoles, String nomEvent);

    public abstract void afficherInfosPageChronos(ArrayList<Chronometreur> chronometreurs, String nomEvent);



    public abstract void ouvrirPageEvenement();

    public abstract void afficherCourseDetaille(Course uneCourse);

    public abstract void afficherCoureurDetaille(Coureur unCoureur);

    public abstract void selectInfosPageCourses(ArrayList<Course> listeCourse, String nom);

    public abstract void afficherInfosPageMateriel(ArrayList<Materiel> materiels);

    public abstract void afficherParking(ArrayList<Parking> parkings);

    public abstract void afficherMatériel(ArrayList<Materiel> materiels);
}
