package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import fr.uga.iut2.genevent.util.EvenementUtilitaire;

import java.io.Serializable;
import java.util.*;

//Classe fini
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation

    private String label;
    private double longueur;
    private int prix;
    private CategorieCoureur categorieMinimumParticipation;
    private Date date;

    private String lieux;
    private int nbCoureurMax;
    private HashMap<String, Integer> lotpremièrePlace = new HashMap<>();
    private HashMap<String, Integer> lotDeuxièmePlace = new HashMap<>();
    private HashMap<String, Integer> lotTroisièmePlace = new HashMap<>();
    private HashMap<String, Integer> lotpremièrePlaceCategorie = new HashMap<>();
    private HashMap<String, Integer> lotInscription = new HashMap<>();
    private HashMap<String, Double> classement = new HashMap<>();
    private HashMap<String, Double> classementHomme = new HashMap<>();
    private HashMap<String, Double> classementFemme = new HashMap<>();
    private ArrayList<Coureur> listeCoureur = new ArrayList<>();
    private ArrayList<Benevole> listeBenevole = new ArrayList<>();
    private ArrayList<Chronometreur> listeChrono = new ArrayList<>();
    private int maxCoureur;
    private Parcours parcours;
    private Evenement evenement;
    //*##########################  CONSTRUCTEUR  ########################

    public Course(String label, double longueur, Evenement evenement, int nbMaxCoureur,Date date,int prix) {
        setLabel(label);
        setLongueur(longueur);
        setEvenement(evenement);
        setMaxCoureur(nbMaxCoureur);
        setDate(date);
        setPrix(prix);
        setAgeMin();
    }

    /**
     * Cette fonction trie la map entrer en paramètre selon ses entrée
     *
     * @param map correspond à la collection map à trié
     * @return
     */
    private static HashMap sort(HashMap map) {
        List linkedlist = new LinkedList(map.entrySet());
        Collections.sort(linkedlist, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        HashMap sortedHashMap = new LinkedHashMap();
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Cette fonction défini la date de la course
     *
     * @param date est de type Date et correpsond à la date de la course
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieux() {
        return lieux;
    }

    /**
     * Cette fonction défini le lieu où se déroulera la course
     *
     * @param lieux est de type String et correspond au lieu ou se déroule la course
     * @throws MauvaisFormatException
     */
    public void setLieux(String lieux) throws MauvaisFormatException {
        EvenementUtilitaire.verifierCoordonnees(lieux);
        this.lieux = lieux;
    }

    /*######################  GET #####################*/
    public String getLabel() {
        return label;
    }

    /**
     * Cette fonction defini le label de la course c'est à dire son titre
     *
     * @param label est de type String et correspond au nom de la course
     */
    public void setLabel(String label) {
        this.label = label;
    }

    public double getLongueur() {
        double temp = longueur;
        return temp;
    }

    /**
     * Cette fonction défini la longueur de la course
     *
     * @param longueur est de type double et correspond à la longueur de la course
     */

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public int getPrix() {
        int temp = prix;
        return temp;
    }

    /**
     * Cette foncion défini le prix d'inscription à la course
     *
     * @param prix est de type int et correspond au prix de la course
     */

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public ArrayList<Coureur> getListeCoureur() {
        ArrayList<Coureur> temp = listeCoureur;
        return temp;
    }

    /**
     * Cette fonction défini la liste de Coureurs
     *
     * @param listeCoureur est une ArrayList de Coureur
     */

    public void setListeCoureur(ArrayList<Coureur> listeCoureur) {
        this.listeCoureur = listeCoureur;
    }

    public int getMaxCoureur() {
        int temp = maxCoureur;
        return temp;
    }

    /**
     * Cette fonction défini le nombre maximum de coureur
     *
     * @param maxCoureur
     */
    public void setMaxCoureur(int maxCoureur) {
        this.maxCoureur = maxCoureur;
    }

    public ArrayList<Chronometreur> getListeChrono() {
        return listeChrono;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    /**
     * Cette fonction défini l'évènement auquel la course appartien
     *
     * @param evenement est de type Evenement et correpsond à l'evenement de la course
     */
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    /**
     * Cette fonction défni la catégorie minimum a avoir pour participer à la course
     */
    public void setAgeMin(){
        if(longueur<1.3){
            this.categorieMinimumParticipation=CategorieCoureur.BABYATHLE;
        } else if (longueur<1.5){
            this.categorieMinimumParticipation=CategorieCoureur.U12_POUSINS;
        } else if (longueur<3){
            this.categorieMinimumParticipation=CategorieCoureur.U14_BENJAMINS;
        } else if(longueur<5){
            this.categorieMinimumParticipation=CategorieCoureur.U16_MINIMES;
        } else if(longueur<15){
            this.categorieMinimumParticipation=CategorieCoureur.U18_Cadets;
        } else if(longueur<25){
            this.categorieMinimumParticipation=CategorieCoureur.U20_JUNIORS;
        } else{
            this.categorieMinimumParticipation=CategorieCoureur.U23_ESPOIRS;
        }
    }



    public HashMap<String, Integer> getLotInscription() {
        HashMap<String, Integer> temp = lotInscription;
        return temp;
    }

    public HashMap<String, Double> getClassement() {
        return classement;
    }

    public ArrayList<Benevole> getListeBenevole() {
        return listeBenevole;
    }

    public int getNbCoureurMax() {
        return nbCoureurMax;
    }

    public CategorieCoureur getCategorieMinimumParticipation() {
        return categorieMinimumParticipation;
    }
    /*#######################  SET  #######################*/

    /**
     * Cette fonction défini la liste de Bénénvoles
     *
     * @param listeBenevole est une ArrayList de Benevole
     */
    public void setListeBenevole(ArrayList<Benevole> listeBenevole) {
        this.listeBenevole = listeBenevole;
    }

    public Parcours getParcours() {
        Parcours temp = parcours;
        return temp;
    }

    /**
     * Cette fonction défini le parcours de la course
     *
     * @param parcours est de type Parcours
     */
    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    /**
     * Cette fonction renvoie un classement des coureurs faisant partie de la categorie entrer en paramètre
     *
     * @param categorie correspond à une CategorieCoureur
     * @return
     */
    public ArrayList<Coureur> getClassementCategorie(CategorieCoureur categorie) {
        ArrayList<Coureur> classementCat = new ArrayList<>();
        for (Coureur coureur : listeCoureur) {
            if (coureur.getCategorie().equals(categorie)) {
                classementCat.add(coureur);
            }
        }
        return classementCat;
    }

    /**
     * Cette fonction initie le classement générale de la course
     */
    public void setClassement() {
        for (Coureur coureur : listeCoureur) {
            classement.put(coureur.getId(), coureur.getTemps());
        }
        classement = sort(classement);

    }

    /**
     * Cette fonction initie le classement de la course selon le sexe du coureur
     */
    public void setClassementSexe() {
        ArrayList<Coureur> listeSexe = new ArrayList<>();
        //défini la liste de coureur homme
        for (Coureur coureur : listeCoureur) {
            if (coureur.getSexe().equals("Homme")) {
                listeSexe.add(coureur);
            }
        }
        //Défini le classement homme de la course
        for (Coureur coureur : listeSexe) {
            classementHomme.put(coureur.getId(), coureur.getTemps());
        }
        classementHomme = sort(classementHomme);
        listeSexe.clear();
        //défini la liste de coureur femme
        for (Coureur coureur : listeCoureur) {
            if (coureur.getSexe().equals("Femme")) {
                listeSexe.add(coureur);
            }
        }
        //défini le classement femme de la course
        for (Coureur coureur : listeSexe) {
            classementFemme.put(coureur.getId(), coureur.getTemps());
        }
        classementFemme = sort(classementFemme);

    }



    /* ##############  METHODES  ##################*/

    /**
     * Cette fonction défini la liste de chronometreurs
     *
     * @param listeChronometreurs est une Arraylist de Chronometreurs
     */
    public void setListeChronometreurs(ArrayList<Chronometreur> listeChronometreurs) {
        this.listeChrono = listeChrono;
    }

    /**
     * Cette fonction enlève à la liste de matériel de l'évenement la liste des lots gagner par un coureur lors de son inscription
     *
     * @param coureur correspond au coureur qui gagne le lot de l'inscription
     */
    public void attribueLotsInscription(Coureur coureur) {
        List linkedlist = new LinkedList(lotInscription.keySet());
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            for (Materiel materiel : evenement.getLstMateriel()) {
                if (it.next().equals(materiel.getNom())) {
                    materiel.setQuantite(materiel.getQuantite() - lotInscription.get(it.next()));
                    evenement.getFinance().addDepense((lotInscription.get(it.next()) * materiel.getPrixLocation()));
                }
            }
        }

    }

    /**
     * Cette fonction enlève à la liste de matériel de l'évenement la liste des lots gagner par le vainqueur de la course
     *
     * @param coureur correspond au coureur qui gagne le lot de l'inscription
     */
    public void attribueLotsVainqueur(Coureur coureur) {
        List linkedlist = new LinkedList(lotpremièrePlace.keySet());
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            for (Materiel materiel : evenement.getLstMateriel()) {
                if (it.next().equals(materiel.getNom())) {
                    materiel.setQuantite(materiel.getQuantite() - lotpremièrePlace.get(it.next()));
                    evenement.getFinance().addDepense((lotpremièrePlace.get(it.next()) * materiel.getPrixLocation()));
                }
            }
        }

    }

    /**
     * Cette fonction enlève à la liste de matériel de l'évenement la liste des lots gagner par le coureur étant arrivé deuxième de la course
     *
     * @param coureur correspond au coureur qui gagne le lot de l'inscription
     */
    public void attribueLotsDeuxièmePlace(Coureur coureur) {
        List linkedlist = new LinkedList(lotDeuxièmePlace.keySet());
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            for (Materiel materiel : evenement.getLstMateriel()) {
                if (it.next().equals(materiel.getNom())) {
                    materiel.setQuantite(materiel.getQuantite() - lotDeuxièmePlace.get(it.next()));
                    evenement.getFinance().addDepense((lotDeuxièmePlace.get(it.next()) * materiel.getPrixLocation()));
                }
            }
        }

    }

    /**
     * Cette fonction enlève à la liste de matériel de l'évenement la liste des lots gagner par le coureur étant arrivé troisième de la course
     *
     * @param coureur correspond au coureur qui gagne le lot de l'inscription
     */
    public void attribueLotsTroisièmePlace(Coureur coureur) {
        List linkedlist = new LinkedList(lotTroisièmePlace.keySet());
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            for (Materiel materiel : evenement.getLstMateriel()) {
                if (it.next().equals(materiel.getNom())) {
                    materiel.setQuantite(materiel.getQuantite() - lotTroisièmePlace.get(it.next()));
                    evenement.getFinance().addDepense((lotTroisièmePlace.get(it.next()) * materiel.getPrixLocation()));
                }
            }
        }

    }

    /**
     * Cette fonction enlève à la liste de matériel de l'évenement la liste des lots gagner par le coureur ayant étant  arriver le premier de sa catégorie
     *
     * @param coureur correspond au coureur qui gagne le lot de l'inscription
     */
    public void attribueLotsVainqueurCategorie(Coureur coureur) {
        List linkedlist = new LinkedList(lotpremièrePlaceCategorie.keySet());
        for (Iterator it = linkedlist.iterator(); it.hasNext(); ) {
            for (Materiel materiel : evenement.getLstMateriel()) {
                if (it.next().equals(materiel.getNom())) {
                    materiel.setQuantite(materiel.getQuantite() - lotpremièrePlaceCategorie.get(it.next()));
                    evenement.getFinance().addDepense((lotpremièrePlaceCategorie.get(it.next()) * materiel.getPrixLocation()));
                }
            }
        }
    }

    /**
     * Cette focntion ajoute le coureur en paramètre dans la liste de coureurs
     *
     * @param coureur est de type Coureur et correspond à uun coureur qui s'est inscrit à la course
     */
    public void addCoureur(Coureur coureur) {
        this.listeCoureur.add(coureur);
    }
    /* ##############  Inutile pour l'instant ##################*/
//    public void setStocksLots(HashMap<String,Integer> stocksLots) {
//        this.stocksLots = stocksLots;
//    }
//
//    public void setLotInscription(HashMap<String,Integer> lotInscription) {
//        this.lotInscription = lotInscription;
//    }
}
