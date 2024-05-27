package fr.uga.iut2.genevent.util;

import fr.uga.iut2.genevent.modele.CategorieCoureur;
import fr.uga.iut2.genevent.modele.Coureur;
import fr.uga.iut2.genevent.modele.Course;

import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CourseUtilitaire {
    /**
     * Cette fonction affiche le classement de la course prit en paramètre
     *
     * @param course est de type Course
     */
    public static void afficheClassement(Course course) {
        Set set2 = course.getClassement().entrySet();
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Cette fonction saisi le temps d'un coureur
     *
     * @param temps   est de type double
     * @param coureur est de type Coureur
     */
    public void saisiTemps(double temps, Coureur coureur) {
        coureur.setTemps(temps);
    }


    public static boolean coureurAutorisé(Date date, Course course) {
        CategorieCoureur catmin = course.getCategorieMinimumParticipation();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date datectu = Date.from(course.getEvenement().getDateDebut().atStartOfDay(defaultZoneId).toInstant());
        long elapsedms = datectu.getTime() - date.getTime();
        long diff = (long) (elapsedms / 31557600000.0);
        int age = (int) (diff);

        if (catmin == CategorieCoureur.BABYATHLE) {
            return true;
        } else if (catmin == CategorieCoureur.U12_POUSINS) {
            return age > 11;
        } else if (catmin == CategorieCoureur.U14_BENJAMINS) {
            return age > 13;
        } else if (catmin == CategorieCoureur.U16_MINIMES) {
            return age > 15;
        } else if (catmin == CategorieCoureur.U18_Cadets) {
            return age > 17;
        } else if (catmin == CategorieCoureur.U20_JUNIORS) {
            return age > 19;
        } else {
            return age > 22;
        }

    }

}
