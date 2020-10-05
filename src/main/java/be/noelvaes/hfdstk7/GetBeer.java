package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Beers;

import javax.persistence.*;
import java.util.List;

public class GetBeer {
    public static void main(String[] args) {
        String pgmName = GetBeer.class.getSimpleName();
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("mysqlcontainer");
            em = emf.createEntityManager();
            EntityTransaction tx =em.getTransaction();
            TypedQuery<Beers> query = em.createNamedQuery("getAllBeers", Beers.class);

            System.out.println(pgmName + " - Start txn");
            tx.begin();
            List<Beers> beers = query.getResultList();
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
