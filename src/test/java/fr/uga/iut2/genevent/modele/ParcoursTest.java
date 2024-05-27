package fr.uga.iut2.genevent.modele;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParcoursTest {
    /**
     * Cette fonction est le test unitaire de la fonction setCoordoneesDepart
     * @throws RuntimeException
     */
    @Test
    void setCoordoneesDepart() throws RuntimeException{
        ArrayList<String> listCoordonnees = new ArrayList<String>(Arrays.asList("0.257458, 69.4657","45.58955, -79.458751"));
        assertThrows(RuntimeException.class, () -> {
            new Parcours(".5454, -5.555","47.589412, -8.254", listCoordonnees);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("-999.5454, -5.555","47.589412, -8.254", listCoordonnees);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("0.5454, 789.555","47.589412, -8.254", listCoordonnees);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("5.454, -5555","47.589412, -8.254", listCoordonnees);
        }, "Problème mauvais format exception");

        assertDoesNotThrow(() -> {
            new Parcours("5.454, -5.555","47.589412, -8.254", listCoordonnees);
        }, "Problème impossible d'entrer le bon format");

    }

    /**
     * Cette fonction est le test unitaire de la fonction setCoordonnesArrivee
     * @throws RuntimeException
     */
    @Test
    void setCoordonnesArrivee() throws RuntimeException{
        ArrayList<String> listCoordonnées = new ArrayList<>(Arrays.asList("0.257458, 69.4657","45.58955, -79.458751"));
        assertThrows(RuntimeException.class, () -> {
            new Parcours("47.589412, -8.254",".5454, -5.555", listCoordonnées);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("47.589412, -8.254","-999.5454, -5.555", listCoordonnées);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("47.589412, -8.254","0.5454, 789.555", listCoordonnées);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("47.589412, -8.254","5454, -5.555", listCoordonnées);
        }, "Problème mauvais format exception");

        assertDoesNotThrow(() -> {
            new Parcours("47.589412, -8.254","5.454, -5.555", listCoordonnées);
        }, "Problème impossible d'entrer le bon format");
    }

    /**
     * Cette fonction est le test unitaire de la fonction setCoordonneRavitaillement
     * @throws RuntimeException
     */

    @Test
    void setCoordonnesRavitaillement() throws RuntimeException{
        ArrayList<String> listCoordonnées = new ArrayList<>(Arrays.asList(".5454, -5.555","0.257458, 69.4657","45.58955, -79.458751"));
        ArrayList<String> listCoordonnées2 = new ArrayList<>(Arrays.asList("-999.5454, -5.555","0.257458, 69.4657","45.58955, -79.58751"));
        ArrayList<String> listCoordonnées3 = new ArrayList<>(Arrays.asList("0.5454, 789.555","0.257458, 69.4657","45.58955, -79.458751"));
        ArrayList<String> listCoordonnées4 = new ArrayList<>(Arrays.asList("5454, -5.555","0.257458, 69.4657","45.58955, -79.458751"));
        ArrayList<String> listCoordonnées5 = new ArrayList<>(Arrays.asList("5.454, -5.555","0.257458, 69.4657","45.58955, -79.458751"));

        assertThrows(RuntimeException.class, () -> {
            new Parcours("0.5454, -5.555","47.589412, -8.254", listCoordonnées);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("-9.5454, -5.555","47.589412, -8.254", listCoordonnées2);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("0.5454, 79.555","47.589412, -8.254", listCoordonnées3);
        }, "Problème mauvais format exception");
        assertThrows(RuntimeException.class, () -> {
            new Parcours("5.454, -5.555","47.589412, -8.254", listCoordonnées4);
        }, "Problème mauvais format exception");

        assertDoesNotThrow(() -> {
            new Parcours("5.454, -5.555","47.589412, -8.254", listCoordonnées5);
        }, "Problème impossible d'entrer le bon format");
    }


}