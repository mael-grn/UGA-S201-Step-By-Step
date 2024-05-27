package fr.uga.iut2.genevent.modele;

import java.io.Serializable;

public enum CategorieCoureur implements Serializable {

BABYATHLE("Baby Athlé"),
    EVEILATHLETIQUE("Éveil Athlétique"),
    U12_POUSINS("U12/Poussins"),
    U14_BENJAMINS("U14/Benjamins"),
    U16_MINIMES("U16/Minimes"),
    U18_Cadets("U18/Cadets"),
    U20_JUNIORS("U20 / Juniors"),
    U23_ESPOIRS("U23/Espoirs"),
    SENIORS("Seniors"),
    MASTERS0("Masters0"),
    MASTERS1("Masters1"),
    MASTERS2("Masters2"),
    MASTERS3("Masters3"),
    MASTERS4("Masters4"),
    MASTERS5("Masters5"),
    MASTERS6("Masters6"),
    MASTERS7("Masters7"),
    MASTERS8("Masters8"),
    MASTERS9("Masters9"),
    MASTERS10("Masters10");
private String libelle;
    //*##########################  CONSTRUCTEUR  ########################
private CategorieCoureur(String libelle){
    this.libelle=libelle;}
    @Override
    public String toString() {
        return libelle;
    }
}
