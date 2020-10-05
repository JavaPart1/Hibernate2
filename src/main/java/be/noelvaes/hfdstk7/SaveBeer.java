package be.noelvaes.hfdstk7;

import be.noelvaes.hfdstk7.domain.Beers;
import be.noelvaes.hfdstk7.domain.Brewer;
import be.noelvaes.hfdstk7.domain.Categories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class SaveBeer {
    public static void main(String[] args) {
        String pgmName = SaveBeer.class.getSimpleName();
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("mysqlcontainer");
            em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            // Domain creations
            List<Beers> lagerbeers = new ArrayList<>();
            List<Beers> blondbeers = new ArrayList<>();
            List<Beers> abinbevbeers = new ArrayList<>();
            List<Beers> maesbeers = new ArrayList<>();
            // Categories
            Categories lager = new Categories("Lager");
            Categories blond = new Categories("Blond");
            Categories dark = new Categories("Dark");
            // Beers & Categories
            Beers stella = new Beers("Stella",1,6000,3,lager);
            Beers leffe = new Beers("Leffe",3,8000,5,blond);
            Beers hoegaardenGC = new Beers("Hoegaarden Grand Cru",3,4000,5,blond);
            // Brewers
            Brewer aBinbev = new Brewer("ABinbev","beukestraat","1000","Brussel",5000);
            Brewer maes = new Brewer("Maes","berkestraat","1000","Brussel",3000);
            // Beers & brewers
            stella.setBrewer(aBinbev);
            leffe.setBrewer(aBinbev);
            hoegaardenGC.setBrewer(maes);
            // Categories links
            lagerbeers.add(stella);
            blondbeers.add(leffe);
            blondbeers.add(hoegaardenGC);
            lager.setBeers(lagerbeers);
            blond.setBeers(blondbeers);
            // Brewers links
            abinbevbeers.add(stella);
            abinbevbeers.add(leffe);
            maesbeers.add(hoegaardenGC);
            aBinbev.setBeers(abinbevbeers);
            maes.setBeers(maesbeers);
            System.out.println(stella.toString());
            System.out.println(leffe.toString());
            System.out.println(hoegaardenGC.toString());
            System.out.println(pgmName + " - Start txn");
            tx.begin();
            em.persist(stella);
            em.persist(leffe);
            em.persist(hoegaardenGC);
            tx.commit();
            System.out.println(pgmName + " - Commit txn");
            System.out.println(pgmName + " - Save executed");
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }

    }
}
