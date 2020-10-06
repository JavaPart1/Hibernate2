package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Brewer;
import be.noelvaes.hfdstk7.domain.Categories;

import javax.persistence.*;
import java.util.List;

public class AddBrewer {
    public static void main(String[] args) {
        String pgmName = AddBrewer.class.getSimpleName();
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("mysqlcontainer");
            em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            // Get existing brewers
            TypedQuery<Brewer> queryBrewer = em.createNamedQuery("getAllBrewers", Brewer.class);
            System.out.println(pgmName + " - Start txn Get all brewers");
            tx.begin();
            List<Brewer> brewerList  = queryBrewer.getResultList();
            tx.commit();

            // Domain creations
            // Brewers
            Brewer newBrewer = new Brewer("Abdij van Westmalle","Centrumstraat","5700","Westmalle",200);
            // Check if new brewer exists
            if (brewerList.stream().anyMatch(c -> c.getBrewerName().equals(newBrewer.getBrewerName()))) {
                // new brewer already exists
                System.out.println("New category " + newBrewer.getBrewerName() + " already exists");
            } else {
                System.out.println(pgmName + " - Start txn Create new brewer");
                tx.begin();
                em.persist(newBrewer);
                tx.commit();
                System.out.println(pgmName + " - Commit txn");
            }
            System.out.println(pgmName + " - End of program");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }

    }
}
