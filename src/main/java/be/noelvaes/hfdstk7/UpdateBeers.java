package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Beers;

import javax.persistence.*;
import java.util.List;

public class UpdateBeers {
    public static void main(String[] args) {
        String pgmName = UpdateBeers.class.getSimpleName();
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("mysqlcontainer");
            em = emf.createEntityManager();
            EntityTransaction tx =em.getTransaction();
            // Get old data
            TypedQuery<Beers> queryGet = em.createNamedQuery("getAllBeers", Beers.class);
            System.out.println(pgmName + " - Start Get old data txn");
            tx.begin();
            List<Beers> beers = queryGet.getResultList();
            tx.commit();
            System.out.println(pgmName + " - Commit txn");
            for (Beers b: beers) {
                System.out.println(b.toString());
            }
            // Update data
            Query queryUpdate = em.createNamedQuery("updatePriceBeers");
            queryUpdate.setParameter("rise",(float)1.02);
            System.out.println(pgmName + " - Start Update txn");
            tx.begin();
            int result = queryUpdate.executeUpdate();
            tx.commit();
            System.out.println(pgmName + " - Commit txn");
            // Get old data
            System.out.println(pgmName + " - Start Get new data txn");
            tx.begin();
            em.clear();// Clear persistence context
            beers = queryGet.getResultList();
            tx.commit();
            System.out.println(pgmName + " - Commit txn");
            for (Beers b: beers) {
                System.out.println(b.toString());
            }
            System.out.println(pgmName + " - End of program");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }

    }

}
