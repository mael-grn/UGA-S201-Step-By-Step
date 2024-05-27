package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.MaterielNonTrouveException;
import fr.uga.iut2.genevent.Exception.MauvaisFormatException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EvenementTest {
    /**
     * Cette fonction est le test unitaire de la fonction perteMateriel
     * @throws MaterielNonTrouveException
     */
    @Test
    void perteMateriel() throws MaterielNonTrouveException {
        Evenement event = new Evenement("test", new Organisateur("orga","orgaa","orga@gmail.com",new Date(25)));
        event.addMaterielCaution("test",5,18,58);

        assertThrows(MaterielNonTrouveException.class,() -> {event.perteMateriel("testt",50);},"Le test doit renvoyer une erreur car le produit n'est pas dans la liste");
        assertDoesNotThrow(() -> {event.perteMateriel("test",1);},"Aucune exception ne doit être levé");


        Evenement event2 = new Evenement("test", new Organisateur("orga","orgaa","orga@gmail.com",new Date(25)));
        event2.addMaterielCaution("mat1",10,10,14);
        event2.perteMateriel("mat1",4);

        assertEquals(56,event2.getFinance().getDepense(),"La valeur ajouter à dépense n'est pas correct");
        assertEquals(84,event2.getFinance().getCaution(), "La caution apres la MAJ des pertes passe à 84 euros");
        assertEquals(56,event2.getFinance().getHistoriquePerte().get(event2.getFinance().getHistoriquePerte().size()-1).getMontant(), "La dernière dépense lié à la perte de la caution doit apparaitre dans historiquePerte");
    }

    /**
     * Cette fonction est le test unitaire de la fonction addMaterielCaution
     */
    @Test
    void addMaterielCaution() {
        Evenement event = new Evenement("test", new Organisateur("orga","orgaa","orga@gmail.com",new Date(25)));
        event.addMaterielCaution("mat",5,10,150);

        assertEquals(50,event.getFinance().getDepense());
        assertEquals(1500,event.getFinance().getCaution());
    }

    /**
     * Cette fonction est le test unitaire de la fonction addMaterielSansCaution
     */
    @Test
    void addMaterielSansCaution() {
        Evenement event = new Evenement("test", new Organisateur("orga","orgaa","orga@gmail.com",new Date(25)));
        event.addMaterielSansCaution("mat",5,10);

        assertEquals(50,event.getFinance().getDepense());
        assertEquals(0,event.getFinance().getCaution(),"Le prix de la caution doit etre égale à 0");
    }

    /**
     * Cette fonction est le test unitaire de la fonction removeMateriel
     */

    @Test
    void removeMateriel() {
        Evenement event = new Evenement("test", new Organisateur("orga","orgaa","orga@gmail.com",new Date(25)));

        assertThrows(MaterielNonTrouveException.class, () -> {
            event.removeMateriel("test",5);
        }, "Problème exception non levé pour un produit qui n'est pas présent dans la liste");


        event.addMaterielCaution("mat1",50,5,45);

        assertDoesNotThrow(() -> {
            event.removeMateriel("mat1", 8);
        }, "Problème lors de la suppression de materiel");
    }
}