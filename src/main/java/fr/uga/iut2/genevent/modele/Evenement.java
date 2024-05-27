package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.ConsomableInsufisantException;
import fr.uga.iut2.genevent.Exception.MaterielNonTrouveException;
import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import fr.uga.iut2.genevent.util.EvenementUtilitaire;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private ArrayList<Course> listeCourse = new ArrayList<>();



    private ArrayList<Coureur> lstCoureur = new ArrayList<>();
    private ArrayList<Benevole> lstBenevole = new ArrayList<>();
    private ArrayList<Materiel> lstMateriel = new ArrayList<>();

    private ArrayList<Chronometreur> lstChronometreur = new ArrayList<>();
    private Organisateur organisateur;

    private String nom;



    private ArrayList<Parking> parkings = new ArrayList<Parking>();
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Finance finance = new Finance(0, this);

    // Invariant de classe : !dateDebut.isAfter(dateFin)
    //     On utilise la négation ici pour exprimer (dateDebut <= dateFin), ce
    //     qui est équivalent à !(dateDebut > dateFin).

//    public static Evenement initialiseEvenement(GenEvent genevent, String nom, LocalDate dateDebut, LocalDate dateFin, Utilisateur admin) {
//        Evenement evt = new Evenement(genevent, nom, dateDebut, dateFin);
//        evt.ajouteAdministrateur(admin);
//        return evt;
//    }

    //*##########################  CONSTRUCTEUR  ########################

    public Evenement(String nom, Organisateur organisateur) {
        this.nom = nom;
        setOrganisateur(organisateur);
    }

    public Evenement(String nom, Organisateur organisateur, LocalDate dateDebut, LocalDate dateFin) {
        this.nom = nom;
        setDateDebut(dateDebut);
        setDateFin(dateFin);
        setOrganisateur(organisateur);
    }

    //*##########################  GETTERS  ########################

    public ArrayList<Coureur> getLstCoureur() {
            return lstCoureur;
    }

    public ArrayList<Benevole> getLstBenevole() {
        return lstBenevole;
    }

    public ArrayList<Chronometreur> getLstChronometreur() {
        return lstChronometreur;
    }

    public Organisateur getOrganisateur() {
        return organisateur;
    }


    public String getNom() {
        return this.nom;
    }

    public ArrayList<Course> getListeCourse() {
        return listeCourse;
    }

    public ArrayList<Materiel> getLstMateriel() {
        return lstMateriel;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Finance getFinance() {
        return finance;
    }
    public ArrayList<Parking> getParkings() {
        if(parkings !=null) {
            return parkings;
        }
        else{
            return new ArrayList<>();
        }
    }
//*##########################  SETTERS  ########################

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Cette fonction défini la date de début de l'évènemement
     * @param dateDebut est de type LocalDate et correspond à la date de début de l'évènement
     */
    public void setDateDebut(LocalDate dateDebut) {
        assert !dateDebut.isAfter(this.dateFin);
        this.dateDebut = dateDebut;
    }

    /**
     * Cette fonction défini l'organisateur de l'évènement
     * @param organisateur est de type Organisateur et correspond à l'organisateur de l'évènement
     */
    public void setOrganisateur(Organisateur organisateur) {
        this.organisateur = organisateur;
    }

    /**
     * Cette fonction défini la finance de l'évènement
     * @param finance est de type Finance
     */
    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    /**
     * Cette fonction défini la date de fin de l'évènement
     * @param dateFin est de type LocalDate et correspond à la date de fin de l'évènement
     */
    public void setDateFin(LocalDate dateFin) {
        assert !this.dateDebut.isAfter(dateFin);
        this.dateFin = dateFin;
    }

    /**
     * Cette fonction défini le parking de l'évènement en vérifiant les coordonées du parking
     * @param libelle est de type String et correpsond au nom du parking
     * @param nbPLaces est de type int et correspond au nombres de places du parking
     * @param coordonee est de type String et correpsond aux coordonées du parking
     * @throws MauvaisFormatException
     */
    public void addParking(String libelle, int nbPLaces, String coordonee) throws MauvaisFormatException {
        EvenementUtilitaire.verifierCoordonnees(coordonee);
        if (this.parkings == null) {
            this.parkings = new ArrayList<Parking>();
        }
        this.parkings.add(new Parking(libelle, nbPLaces, coordonee));
    }

    //######################### OTHER FUNCTIONS ###############################

    /**
     * Cette fonction ajoute à la liste de course de l'évènement une course
     * @param course est de type Course et correpsond à une course à ajouter à l'évènement
     */
    public void addListeCourse(Course course) {
        this.listeCourse.add(course);
    }

    /**
     * La fonction cherche si le materiel entré est present dans la liste de materiel sinon => MaterielNonTrouveException
     * on ajoute la dépense engendré à finance + on enleve cette somme à caution dans finance
     * @param nom nom du materiel
     * @param quantite quantité de materiel dont on doit payer la caution
     * @throws MaterielNonTrouveException
     */
    public void perteMateriel(String nom, int quantite) throws MaterielNonTrouveException{
        boolean changement = false;
        //cherche si le matériel perdu étais posséder
        for (int i = 0; i<lstMateriel.size(); i++){
            if (nom.compareTo(lstMateriel.get(i).getNom()) == 0){
                //Si la quantité est insuffisante et si elle l'est fai
                if (quantite >= lstMateriel.get(i).getQuantite()){
                    finance.setDepense(lstMateriel.get(i).getPrixCaution() * lstMateriel.get(i).getQuantite());
                    finance.setCaution(finance.getCaution() - lstMateriel.get(i).getPrixCaution() * lstMateriel.get(i).getQuantite());
                    finance.addHistoriquePerte(new Transaction("Perte de la caution : "+lstMateriel.get(i).getQuantite()+" "+nom,lstMateriel.get(i).getPrixCaution() * lstMateriel.get(i).getQuantite(),LocalDate.now().toString()));
                }else {
                    finance.setDepense(lstMateriel.get(i).getPrixCaution() * quantite);
                    finance.setCaution(finance.getCaution() - lstMateriel.get(i).getPrixCaution() * quantite);
                    finance.addHistoriquePerte(new Transaction("Perte de la caution : "+quantite+" "+nom,lstMateriel.get(i).getPrixCaution() * quantite,LocalDate.now().toString()));
                }
                changement = true;
            }
        }
        if (!changement){
            throw new MaterielNonTrouveException("Le materiel cherché n'a pas été trouvé");
        }
    }

    /**
     * Cette fonction enlève la quantité de produit vendu au stock et ajoute au gain les ventes effectué
     * @param nom correpsond au produit vendu
     * @param quantite correspond à la quantité de produit vendus
     * @param prixVenteUnite correspond au prix de vente du produit
     * @throws ConsomableInsufisantException
     * @throws MaterielNonTrouveException
     */
    public void venteConsomation(String nom, int quantite, int prixVenteUnite) throws ConsomableInsufisantException,MaterielNonTrouveException {
        Materiel mat = null;
        //cherche le matériel dans la liste de matériels
        for (Materiel materiel:lstMateriel){
            if(materiel.getNom().equals(nom)){
                mat=materiel;
            }
        }
        //Si le matériel est possédé alors onregarde sa quantité
        if(mat!=null){
            //Si la quantité est suffisante on enlève la quantité vendu
                if(mat.getQuantite()>=quantite){
                    finance.setGain(prixVenteUnite*quantite);
                    mat.setQuantite(quantite);

                } else {
                    //Si la quantité est insuffisante alors on lève une exception
                    throw new ConsomableInsufisantException();
                }
        } else {
            //Si l'on a pas de matériel alors on lève une exception
            throw new MaterielNonTrouveException();
        }

    }

    /**
     * Cette fonction verifie si le nom de materiel entrée est deja dans la base de donnée si c'est le cas alors on rajoute la quantité entrée à celle deja présente.
     * si aucun nom de materiel ne correspond on crée une nouveau materiel qu'on ajoute à la liste de materiel.
     * @param nom le nom est un nom unique propre à chaque materiel
     * @param prix le prix entrée doit être superieur ou égale à 0
     * @param quantite la quantité entrée doit etre supèrieur à 0
     * @param prixCaution le prix de la caution doit etre supérieur ou égale à 0
     */
    public void addMaterielCaution(String nom, double prix, int quantite, double prixCaution){
        boolean changement=false;
        //On regarde si l'on possède déjà le matériel
        for(Materiel materiel: lstMateriel){
            if(materiel.getNom().equals(nom)){
                changement=true;
                materiel.setQuantite(quantite);
                finance.addDepense(quantite * materiel.getPrixLocation());
                finance.addHistoriquePerte(new Transaction(quantite+" "+nom,quantite * materiel.getPrixLocation(),LocalDate.now().toString()));
                finance.addCaution(prixCaution * quantite);
            }
        }
        //Si l'on ne possède pas le matériel on le crée et on l'ajoute
        if(!changement){
            lstMateriel.add(new Materiel(nom, prix, quantite, prixCaution));
            finance.addDepense(prix * quantite);
            finance.addHistoriquePerte(new Transaction(quantite+" "+nom,prix * quantite,LocalDate.now().toString()));
            finance.addCaution(prixCaution * quantite);
        }
    }

    /**
     *  Cette fonction verifie si le nom de materiel entrée est deja dans la base de donnée si c'est le cas alors on rajoute la quantité entrée à celle deja présente.
     * si aucun nom de materiel ne correspond on crée une nouveau materiel qu'on ajoute à la liste de materiel. Dans cette fonction le materiel entré n'a pas de prix de location
     * @param nom nom unique propre à chaque materiel
     * @param prix le prix entrée doit être superieur ou égale à 0
     * @param quantite la quantité entrée doit etre supèrieur à 0
     */
    public void addMaterielSansCaution(String nom, double prix, int quantite) {
        boolean changement = false;
        //On regarde si l'on possède déjà le matériel
        for (Materiel materiel : lstMateriel) {
            if (materiel.getNom().equals(nom)) {
                changement = true;
                materiel.setQuantite(materiel.getQuantite()+quantite);
                finance.addDepense(quantite * materiel.getPrixLocation());
            }
        }
        //Si l'on ne possède pas le matériel on le crée et on l'ajoute
        if (!changement) {
            lstMateriel.add(new Materiel(nom, prix, quantite));
            finance.addDepense(quantite*prix);
        }
    }

    /**
     * Supprime la quantite de materiel souhaité ou le materiel si la quantité et égal ou superieur à la quantité présente
     * @param nom nom du materiel à supprimer
     * @param quantite quantite de ce materiel à supprimer
     */
    public void removeMateriel(String nom, int quantite) throws MaterielNonTrouveException{
        boolean changement = false;
        for (int i = 0; i<lstMateriel.size(); i++){
            if (lstMateriel.get(i).getNom().compareTo(nom)==0){
                if (quantite>=lstMateriel.get(i).getQuantite()){
                    lstMateriel.remove(i);
                }else {
                    lstMateriel.get(i).setQuantite(lstMateriel.get(i).getQuantite() - quantite);
                }
                changement = true;
            }
        }
        if (!changement){
            throw new MaterielNonTrouveException("Aucun materiel comportant ce nom n'a été trouvé");
        }
    }
    public void addBenevole(Benevole benevole) {
        this.lstBenevole.add(benevole);
    }

    public void addChronometreur(Chronometreur chronometreur) {
        lstChronometreur.add(chronometreur);
    }

    /**
     * Cette fonction ajoute à la liste de matériel la quantité d'un matériel donnée et si le matériel n'existe pas en stock il le crée
     * @param nom correspond au nom du matériel
     * @param prix correspond au prix d'achat du consomable
     * @param quantite correspond à la quantité que l'on ajoute de ce consombale
     */
    public void addMConsomable(String nom, int prix, int quantite){
        boolean changement=false;
        for(Materiel materiel: lstMateriel){
            if(materiel.getNom().equals(nom)){
                changement=true;
                materiel.setQuantite(quantite);
            }
        }
        if(!changement){
            lstMateriel.add(new Materiel(nom, prix, quantite));
        }
    }

    /**
     * Cette fonction ajoute à la liste de matériel la quantité d'un matériel donnée et si le matériel n'existe pas en stock il le crée
     * @param nom correspond au nom du matériel
     * @param prixLocation correspond au prix de location du materiel
     * @param quantite correspond à la quantité que l'on ajoute du materiel
     * @param prixCaution correspond au prix de la caution du materiel
     */
    public void addMAterielLouer(String nom, int prixLocation, int quantite, int prixCaution){
        boolean changement=false;
        for(Materiel materiel: lstMateriel){
            if(materiel.getNom().equals(nom)){
                changement=true;
                materiel.setQuantite(quantite);
            }
        }
        if(!changement){
            lstMateriel.add(new Materiel(nom, prixLocation, quantite,prixCaution));
        }
    }
}
