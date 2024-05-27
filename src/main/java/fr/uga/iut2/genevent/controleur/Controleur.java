package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import fr.uga.iut2.genevent.Exception.ObjetNonTrouveException;
import fr.uga.iut2.genevent.modele.*;
import fr.uga.iut2.genevent.util.EvenementUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import fr.uga.iut2.genevent.vue.JavaFXGUI;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


/**
 * la classe controleur permets de faire le liens entre l'interface (IHM) et le modele.
 * Cette classe possede un attribut genevent, qui contient toutes les données de l'application.
 */
public class Controleur {

    /**
     * genevent est l'attribut contenant toutes les données (liées au modèle) de l'application.
     */
    private final GenEvent genevent;

    /**
     * corresponds à l'organisateur qui sera sélectionné par l'utilisateur.
     */
    private Organisateur organisateurCourant;

    /**
     *ihm corresponds à l'interface qui sera utilisé.
     */
    private final IHM ihm;

    /**
     * represente l'etat courant du controleur (soit l'etape à laquelle on se situe dans le cheminement de l'application).
     */
    private Etats etat;

    /**
     * représente l'évènement actuel dans l'application, initialisé après qu'un évènement soit sélectionné par l'organisateur
     */
    private Evenement evenementCourant;

    /**
     * représente la course courante dans l'application, initialisée après qu'une course soit sélectionnée, par exemple
     * pour y inscrire un participant
     */
    private Course courseCourante;

    private Coureur coureurCourant;

    private Chronometreur chronoCourant;

    private Benevole benevCourant;




    /**
     * represente tous les etats que peut prendre le controleur. chaque etat correspond à une etape.
     */
    enum Etats {
        CHOIX_ORGA,
        CREATION_ORGA,
        CHOIX_EVT,
        CREATION_EVT;
    }

    //*#######################  CONSTRUCTEUR  #######################


    public Controleur(GenEvent genevent) {

        this.genevent = genevent;

        // choisir la classe CLI ou JavaFXGUI
        //this.ihm = new CLI(this);
        this.ihm = new JavaFXGUI(this);
    }

    public void demarrer() {
        this.ihm.demarrerInteraction();
        etat = Etats.CHOIX_ORGA;
    }


    //*#######################  GETTERS  #######################

    public Coureur getCoureurCourant() {
        return coureurCourant;
    }

    public Benevole getBenevCourant() {
        return benevCourant;
    }

    public Evenement getEvenementCourant() {
        return this.evenementCourant;
    }

    public GenEvent getGenevent() {
        return genevent;
    }

    public Chronometreur getChronoCourant() {
        return chronoCourant;
    }

    public Course getCourseCourante() {
        return courseCourante;
    }

    public Organisateur getOrganisateurCourant(){
        return this.organisateurCourant;
    }

    public String getRevenu(){
        return ""+getEvenementCourant().getFinance().getGain();
    }

    public String getSolde(){
        return ""+getEvenementCourant().getFinance().getBalance();
    }

    /**
     *
     * @return cout total du materiel de l'événement (prix unitaire * quantité)
     */
    public double getCoutMateriel(){
        double tot = 0;
        for(int i = 0; i<evenementCourant.getLstMateriel().size();i++){
            tot = tot+(evenementCourant.getLstMateriel().get(i).getPrixLocation()*evenementCourant.getLstMateriel().get(i).getQuantite());
        }
        return tot;
    }

    public String getDepense(){
        return ""+getEvenementCourant().getFinance().getDepense();
    }

    public ArrayList<Transaction> getDataRevenu(){
        return getEvenementCourant().getFinance().getHistoriqueGain();
    }

    public ArrayList<Transaction> getDataDepense(){
        return evenementCourant.getFinance().getHistoriquePerte();
    }

    //*#######################  SETTERS  #######################

    public void setBenevCourant(Benevole benevCourant) {
        this.benevCourant = benevCourant;
    }

    public void setCourseCourante(Course courseCourante) {
        this.courseCourante = courseCourante;
    }

    public void setChronoCourant(Chronometreur chronoCourant) {
        this.chronoCourant = chronoCourant;
    }

    public void setCoureurCourant(Coureur coureurCourant) {
        this.coureurCourant = coureurCourant;
    }

    public void setNewRevenu(String motif, double montant, String date){
        getEvenementCourant().getFinance().addHistoriqueGain(new Transaction(motif,montant,date));
        getEvenementCourant().getFinance().setGain(montant);
    }

    /**
     * permet d'ajouter une nouvelle dépense dans historique dépense
     * @param motif
     * @param montant
     * @param date
     */
    public void setNewDepense(String motif, double montant, String date){
        getEvenementCourant().getFinance().addHistoriquePerte(new Transaction(motif,montant,date));
        getEvenementCourant().getFinance().setDepense(montant);
    }



    //*#######################  METHODES  #######################


    /**
     * est appelée par l'ihm pour afficher les informations sur la page principale
     */
    public void afficherInfoPagePrincipale() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date dateDeb = Date.from(this.evenementCourant.getDateDebut().atStartOfDay(defaultZoneId).toInstant());
        Date dateFin = Date.from(this.evenementCourant.getDateFin().atStartOfDay(defaultZoneId).toInstant());
        this.ihm.afficherInfosPagePrincipale(this.evenementCourant.getNom(), dateDeb, dateFin, 20, 15,
                this.evenementCourant.getLstCoureur().size(), this.evenementCourant.getLstBenevole().size());
    }


    /**
     * permet d'afficher la liste des organisateurs existants sur la page de connexion.
     */
    public void afficherOrganisateurs() {
        etat = Etats.CHOIX_ORGA;
        this.ihm.afficherListeOrganisateurVbox(genevent.getOrganisateurs());
    }

    /**
     * permet d'ouvrir la page de saisie d'un nouvel organisateur.
     */
    public void saisirNouvelOrganisateur() {
        etat = Etats.CREATION_ORGA;
        this.ihm.saisirNouvelOrganisateur();
    }

    /**
     * permet de passer à la page de selection d'un évènement.
     * @param nomPrenom correspond au nom complet de l'organisateur sélectionné par l'utilisateur.
     *                  Ce nom complet est comparé itérativement avec chaque nom complet de chaques organisateurs, afin de trouvé l'organisateur correspondant.
     *                  Si l'organisateur courant est trouvé, alors on affiche la page des évènement de cet organisateur, sinon on renvois une exeption.
     */
    public void afficherEvenements(String nomPrenom) throws ObjetNonTrouveException {
        etat = Etats.CHOIX_EVT;

        // rechercher l'organisateur correspondant au nomPrenom

        for(int i=0; i<genevent.getOrganisateurs().size(); i++) {
            if (genevent.getOrganisateurs().get(i).getNomComplet().compareToIgnoreCase(nomPrenom)==0){
                organisateurCourant = genevent.getOrganisateurs().get(i);
            }
        }

        if (organisateurCourant != null) {
            this.ihm.afficherPageVosEvenements(genevent.getOrganisateurs().get(genevent.getOrganisateurs().indexOf(organisateurCourant)).getEvenements());
        } else {
            throw new ObjetNonTrouveException();
        }

    }

    /**
     *
     * @param nom corresponds au nom de l'évènement qui a été choisi par l'utilisateur.
     *            Ce nom est au format string, et est donc comparé itérativement avec le nom de chaques évènements afin de trouver l'évènement correspondant.
     *            Si l'évènement est trouvé, alors on opère à un changement de controleur.
     *            Ce controleur servira à gérer les pages principales de l'application, et de gestion de l'évènement precedemment sélectionné.
     */
    public void afficherLEvenement(String nom) throws ObjetNonTrouveException {
        Evenement eventCourant;

            // rechercher l'évènement correspondant au nom

            for(int i=0; i<genevent.getOrganisateurs().get(genevent.getOrganisateurs().indexOf(organisateurCourant)).getEvenements().size();i++){
                 eventCourant = genevent.getOrganisateurs().get(genevent.getOrganisateurs().indexOf(organisateurCourant)).getEvenements().get(i);
                if(eventCourant.getNom().compareToIgnoreCase(nom)==0){
                    evenementCourant = eventCourant;
                }
            }
            if(evenementCourant!=null){

                //Ouvrir la page Evenement
            this.ihm.ouvrirPageEvenement();
            }
            else{
                throw new ObjetNonTrouveException();
            }


        }

    /**
     * Permet de créer un nouvel organisateur.
     * @param nom correspond au nom de l'organisateur.
     * @param prenom correspond au prénom de l'organisateur.
     * @param email correspond à l'email de l'organisateur.
     * @param dateNaissance correspond à la date de naissance de l'organisateur.
     */
    public void ajouterOrganisateur(String nom, String prenom, String email, Date dateNaissance) {
        genevent.addOrganisateur(new Organisateur(nom, prenom, email, dateNaissance));
    }

    /**
     * Permet de créer un évènement, et de l'ajouter à l'organisateur courant.
     * @param nom corresponds au nom de l'évènement.
     * @param dateDebut correspond à sa date de début.
     * @param dateFin correspond à sa date de fin.
     */
    public void ajouterEvent(String nom, LocalDate dateDebut, LocalDate dateFin){
        this.organisateurCourant.addEvenement(new Evenement(nom,this.organisateurCourant,dateDebut,dateFin));
    }

    /**
     * permet de verifier si il existe déjà un évènement de nom nomEvenement
     * @return true si nomEvenement corresponds à un évènement existant déjà, false sinon.
     */
    public boolean nomExistant(String nomEvenement){
        ArrayList<Evenement> listeEvent = organisateurCourant.getEvenements();
        boolean isValid=true;
        if(listeEvent!=null) {
            for (int i = 0; i < listeEvent.size(); i++) {
                if (listeEvent.get(i).getNom().compareToIgnoreCase(nomEvenement) == 0) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }


    /**
     * est appelé par l'ihm pour afficher les informations sur les courses existantes
     */
    public void afficherListeCourseExistante() {
        this.ihm.afficherInfosPageCourses(evenementCourant.getListeCourse(), evenementCourant.getNom());
    }

    /**
     * est appelé pour la sélection d'une course parmi une liste pour la création d'un participant
     */
    public void selectListeCourseExistante() {
        this.ihm.selectInfosPageCourses(evenementCourant.getListeCourse(), evenementCourant.getNom());
    }


    /**
     * recherche si une course existe dans la liste de courses existantes
     * @param nomCourse la course qu'on recherche dans la liste de courses existante
     * @return true si aucune courses n'est trouvée
     */
    public boolean nomExistantCourse(String nomCourse){
        ArrayList<Course> listeCourse = evenementCourant.getListeCourse();
        boolean isValid=true;
        if(listeCourse!=null) {
            for (int i = 0; i < listeCourse.size(); i++) {
                if (listeCourse.get(i).getLabel().compareToIgnoreCase(nomCourse) == 0) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    /**
     * verifie si des coordonées sont au bon format
     * @param coo les coordonées que l'ont souhaite verifier
     * @return true si les coordonées sont au bon format
     */
    public boolean verifierCoordonees(String coo) {
        try {
            EvenementUtilitaire.verifierCoordonnees(coo);
            return true;
        } catch (MauvaisFormatException e) {
            return false;
        }
    }

    /**
     * permet de creer une nouvelle course et de l'ajouter à la liste de course existante
     * @param nom le nom de la course
     * @param date la date de la course
     * @param lieu le lieu de la course, coordonées
     * @param distance la distance (longueur) de la course
     * @param nbrCoureur le nombre maximum de coureur que peut accueillir la course
     * @param prix le prix d'inscription a la course
     * @throws MauvaisFormatException
     */
    public void ajouterCourse(String nom, Date date, String lieu, Double distance, int nbrCoureur, int prix) throws MauvaisFormatException {
        Course uneCourse = new Course(nom, distance, evenementCourant, nbrCoureur, date, prix);
        uneCourse.setLieux(lieu);
        evenementCourant.addListeCourse(uneCourse);
    }

    /**
     * permet de supprimer une course de la liste de courses existantes
     * @param uneCourse la course à supprimer
     */
    public void retirerCourse(Course uneCourse) {
        evenementCourant.getListeCourse().remove(uneCourse);
    }

    /**
     * est appelé par l'ihm pour afficher les details d'une course
     * @param uneCourse la course dont on souhaite afficher les details
     */
    public void afficherCourseDetaille(Course uneCourse){this.ihm.afficherCourseDetaille(uneCourse);}

    public Course findCourse(String uneCourse) {
        Course temp = new Course("", 0, evenementCourant, 0, new Date(), 0);
        for (Course course : evenementCourant.getListeCourse()) {
            if (course.getLabel().compareToIgnoreCase(uneCourse)==0) {
                temp = course;
            }
        }
        return temp;
    }

    /**
     * verifie si un materiel existe dans la liste de materiel
     * @param nomMat le materiel qu'on recherche
     * @return true si le materiel est touvé
     */
    public Boolean matExistant(String nomMat) {
        boolean temp = false;
        for (Materiel mat : evenementCourant.getLstMateriel()) {
            if (mat.getNom().compareToIgnoreCase(nomMat)==0) {
                temp=true;
            }
        }
        return  temp;
    }


    /**
     * appelée pour afficher la liste des coureurs inscrits à l'évènement dans l'onglet participants
     */
    public void afficherListeCoureursExistante() {
        this.ihm.afficherInfosPageCoureurs(evenementCourant.getLstCoureur(), evenementCourant.getNom());
    }

    /**
     * appelée pour afficher la liste des bénévoles inscrits à l'évènement dans l'onglet participants
     */
    public void afficherListeBenevsExistante() {
        this.ihm.afficherInfosPageBenevs(evenementCourant.getLstBenevole(), evenementCourant.getNom());
    }

    /**
     * appelée pour afficher la liste des chronométreurs inscrits à l'évènement dans l'onglet participants
     */
    public void afficherListeChronosExistante() {
        this.ihm.afficherInfosPageChronos(evenementCourant.getLstChronometreur(), evenementCourant.getNom());
    }


    /**
     * appelée pour afficher les informations détaillée d'un coureur NON OPERATIONNEL
     */
    public void afficherCoureurDetaille(Coureur unCoureur){this.ihm.afficherCoureurDetaille(unCoureur);}

    /**
     * permet d'ajouter un nouveau materiel à la liste de materiel
     * @param nom le nom du materiel
     * @param quant la quantité
     * @param prix le prix unitaire
     */
    public void ajouterMateriel(String nom, int quant, double prix) {
        evenementCourant.addMaterielSansCaution(nom, prix, quant);
    }

    public void afficherPageMat() {
        this.ihm.afficherInfosPageMateriel(evenementCourant.getLstMateriel());
    }
    public Transaction getLastTransaction(){
        return getEvenementCourant().getFinance().getHistoriqueGain().get(getEvenementCourant().getFinance().getHistoriqueGain().size()-1);
    }





    /**
     * permet créer deux valeurs par défauts dans historique gain correspondant respectivement à Budget et Inscriptions
     * ainsi qu'une valeur par défaut dans historique dépense correspondant au matéreiel
     */
    public void initialiseEvenement(){
        evenementCourant.getFinance().addHistoriqueGain(new Transaction("Budget", 0,LocalDate.now().toString()));
        evenementCourant.getFinance().addHistoriquePerte(new Transaction("Materiels : ", 0,LocalDate.now().toString()));
        evenementCourant.getFinance().addHistoriqueGain(new Transaction("Inscriptions : ",0,LocalDate.now().toString()));
    }

    public void ajouterParking(String libelle, String coo, int nbPlaces) throws MauvaisFormatException {
        evenementCourant.addParking(libelle, nbPlaces, coo);
    }

    public void afficherParking() {
        this.ihm.afficherParking(evenementCourant.getParkings());
    }

    public void afficherMateriel(){
        this.ihm.afficherMatériel(evenementCourant.getLstMateriel());
    }

    /**
     * parcours de la liste de parking pour verifier si un certain parking y est deja present
     * @param libelle le nom du parking que l'ont recherche
     * @return true si le parking à été trouvé
     */
    public boolean existParking(String libelle) {
        boolean temp = false;
        for (Parking parking : evenementCourant.getParkings()) {
            if (parking.getLibelle().compareToIgnoreCase(libelle)==0) {
                temp = true;
            }
        }
        return temp;
    }

    /**
     * supprime le materiel ainsi que le cout de celui-ci dans finance
     * @param mat
     */
    public void removeMateriel(Materiel mat) {
        evenementCourant.getLstMateriel().remove(evenementCourant.getLstMateriel().indexOf(mat));
        getDataDepense().get(0).setMontant(getCoutMateriel());
        evenementCourant.getFinance().setDepense(-mat.getPrixLocation()*mat.getQuantite());
    }


    public void addInscription(double montant){
        evenementCourant.getFinance().getHistoriqueGain().get(1).setMontant(evenementCourant.getFinance().getHistoriqueGain().get(1).getMontant()+montant);
        evenementCourant.getFinance().setGain(montant);
    }
}
