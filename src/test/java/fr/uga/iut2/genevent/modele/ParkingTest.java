package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.Exception.MauvaisFormatException;

import static org.junit.jupiter.api.Assertions.*;

class ParkingTest {
    /**
     * Cette fonction est le test unitaire de la fonction setCoordones
     * @throws RuntimeException
     */
    @org.junit.jupiter.api.Test
    void setCoordones() throws RuntimeException {
        assertThrows(RuntimeException.class, () -> {
            new Parking("park1", 12, "91.40338, -50.7403");
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parking("park1", 12, "0.40338, -181.7403");
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parking("park1", 12, ".40338, -1.7403");
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parking("park1", 12, "000000.40338, -17403");
        }, "Problème Mauvais format exception");

        assertDoesNotThrow(() -> {new Parking("park1", 12, "1.40338, -50.7403");
    }, "Problème mauvais format exception");
    }
}